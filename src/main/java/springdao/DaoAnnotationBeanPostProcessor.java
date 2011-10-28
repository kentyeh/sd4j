package springdao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;
import javax.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import springdao.support.AbstractSpringDao;

/**
 *
 * @author Kent Yeh
 */
public class DaoAnnotationBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter implements Ordered,
        ApplicationContextAware, InitializingBean {

    private static Logger logger = LoggerFactory.getLogger(DaoAnnotationBeanPostProcessor.class);
    private ApplicationContext context;
    private ConfigurableApplicationContext regContext;
    private static AtomicInteger defcnt = new AtomicInteger(0);

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
        if (applicationContext instanceof ConfigurableApplicationContext) {
            regContext = (ConfigurableApplicationContext) applicationContext;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        assert context.getBean(JpaTransactionManager.class) != null : "No JpaTransactionManager bean found.";
    }

    public <T> T getBean(String name, Class<T> requiredType) {
        try {
            return context.getBean(name, requiredType);
        } catch (NoSuchBeanDefinitionException ex) {
            logger.warn("No bean name '{}'  of {} is defined.", name, requiredType.getSimpleName());
            return null;
        } catch (BeansException ex) {
            logger.warn(String.format("Can't get %s[%s] bean.", requiredType.getSimpleName(), name), ex);
            return null;
        }
    }

    private void registerBean(String name, Object bean) {
        if (regContext != null) {
            regContext.getBeanFactory().registerSingleton(name, bean);
            logger.debug("register {} with {}->{}", new Object[]{name, bean.getClass(), bean});
        }
    }

    private String convertName(String orgName) {
        return String.valueOf(orgName.charAt(0)).toLowerCase() + orgName.substring(1);
    }

    @Override
    public boolean postProcessAfterInstantiation(final Object bean, String beanName) throws BeansException {

        ReflectionUtils.doWithFields(bean.getClass(), new ReflectionUtils.FieldCallback() {

            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                final Dao dao = field.getAnnotation(Dao.class);
                if (dao != null && dao.value() != null) {
                    String daoName = StringUtils.hasText(dao.name()) ? dao.name() : dao.autoRegister() ? String.format("%sDao", convertName(dao.value().getSimpleName())) : null;
                    DaoRepository<?> resultDao = daoName != null && !daoName.isEmpty() ? getBean(daoName, DaoRepository.class) : null;
                    if (resultDao == null) {
                        if (ClassUtils.isAssignable(field.getType(), AbstractSpringDao.class)) {
                            resultDao = new AbstractSpringDao() {

                                private boolean isDebugExport = false;

                                public Class getClazz() {
                                    if (!isDebugExport) {
                                        isDebugExport = true;
                                        logger.debug("{} @Dao annotate field and getClazz() return {}", bean.getClass().getSimpleName(), dao.value().getSimpleName());
                                    }
                                    return dao.value();
                                }
                            };
                            ((JpaDaoSupport) resultDao).setEntityManagerFactory(context.getBean(dao.factory(), EntityManagerFactory.class));
                        } else if (ClassUtils.isAssignable(DaoRepository.class, field.getType())) {
                            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) regContext.getBeanFactory();
                            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
                            beanDefinition.setBeanClass(field.getType());
                            beanDefinition.setLazyInit(false);
                            beanDefinition.setAbstract(false);
                            beanDefinition.setAutowireCandidate(true);
                            beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
                            String def = "_" + String.valueOf(defcnt.getAndAdd(1));
                            if (ClassUtils.isAssignable(JpaDaoSupport.class, field.getType())) {
                                beanDefinition.getPropertyValues().add("entityManagerFactory", context.getBean(dao.factory(), EntityManagerFactory.class));
                            }
                            registry.registerBeanDefinition(daoName + def, beanDefinition);
                            resultDao = (DaoRepository<?>) context.getBean(daoName + def);
                        } else {
                            throw new BeanNotOfRequiredTypeException(field.getName(), DaoRepository.class, field.getType());
                        }
                        if (StringUtils.hasText(daoName)) {
                            logger.debug("Build, inject field and register bean with {}@{}<{}>", new String[]{daoName,
                                        resultDao.getClass().getSimpleName(), dao.value().getSimpleName()});
                            registerBean(daoName, resultDao);
                        } else {
                            logger.debug("Build, and inject field with bean {}@{}<{}>", new String[]{daoName, resultDao.getClass().getSimpleName(), dao.value().getSimpleName()});
                        }
                    }
                    ReflectionUtils.makeAccessible(field);
                    field.set(bean, resultDao);
                }
                final DaoManager ormm = field.getAnnotation(DaoManager.class);
                if (ormm != null && ormm.value() != null) {
                    String mgrName = StringUtils.hasText(ormm.name()) ? ormm.name() : ormm.autoRegister()
                            ? String.format("%sManager", convertName(ormm.value().getSimpleName())) : null;
                    RepositoryManager resultManager = mgrName != null && !mgrName.isEmpty() ? getBean(mgrName, RepositoryManager.class) : null;
                    Class<?> fc = field.getType();
                    if (resultManager == null) {
                        //target's type equals or extends from ormm.baseManagerType();
                        if (ClassUtils.isAssignable(ormm.baseManagerType(), fc)) {
                            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) regContext.getBeanFactory();
                            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
                            beanDefinition.setBeanClass(fc);
                            beanDefinition.setLazyInit(false);
                            beanDefinition.setAbstract(false);
                            beanDefinition.setAutowireCandidate(true);
                            beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
                            String def = "_" + String.valueOf(defcnt.getAndAdd(1));
                            registry.registerBeanDefinition(mgrName + def, beanDefinition);
                            resultManager = (RepositoryManager) context.getBean(mgrName + def);
                        } else if (ClassUtils.isAssignable(fc, ormm.baseManagerType())) {
                            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) regContext.getBeanFactory();
                            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
                            beanDefinition.setBeanClass(ormm.baseManagerType());
                            beanDefinition.setLazyInit(false);
                            beanDefinition.setAbstract(false);
                            beanDefinition.setAutowireCandidate(true);
                            beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
                            String def = "_" + String.valueOf(defcnt.getAndAdd(1));
                            registry.registerBeanDefinition(mgrName + def, beanDefinition);
                            resultManager = (RepositoryManager) context.getBean(mgrName + def);
                        }
                    }
                    if (resultManager == null) {
                        throw new BeanNotOfRequiredTypeException(mgrName == null || mgrName.isEmpty()
                                ? ClassUtils.getShortName(field.getType()) : mgrName, field.getType(), ormm.baseManagerType());
                    } else if (resultManager.getDao() == null) {
                        String daoName = StringUtils.hasText(ormm.daoName()) ? ormm.daoName() : ormm.autoRegister()
                                ? String.format("%sDao", convertName(ormm.value().getSimpleName())) : null;
                        DaoRepository<?> resultDao = StringUtils.hasText(daoName) ? getBean(daoName, DaoRepository.class) : null;
                        if (resultDao == null) {
                            resultDao = new AbstractSpringDao() {

                                private boolean isDebugExport = false;

                                public Class getClazz() {
                                    if (!isDebugExport) {
                                        isDebugExport = true;
                                        logger.debug("{} @DaoManager annotate field and getClazz() return {}", bean.getClass().getSimpleName(), ormm.value().getSimpleName());
                                    }
                                    return ormm.value();
                                }
                            };
                            ((JpaDaoSupport) resultDao).setEntityManagerFactory(context.getBean(ormm.factory(), EntityManagerFactory.class));
                            if (StringUtils.hasText(daoName)) {
                                registerBean(daoName, resultDao);
                            }
                        }
                        resultManager.setDao(resultDao);
                    }
                    if (StringUtils.hasText(mgrName)) {
                        registerBean(mgrName, resultManager);
                    }
                    ReflectionUtils.makeAccessible(field);
                    logger.debug("Inject {} with {}", field.getName(), resultManager.getClass());
                    field.set(bean, resultManager);
                }
            }
        });
        ReflectionUtils.doWithMethods(bean.getClass(), new ReflectionUtils.MethodCallback() {

            public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {

                Class<?> parmatypes[] = method.getParameterTypes();
                Class<?> fc = parmatypes.length > 0 ? parmatypes[0] : null;

                final Dao dao = method.getAnnotation(Dao.class);
                if (dao != null && dao.value() != null && fc != null && void.class.equals(method.getReturnType())
                        && ClassUtils.isAssignable(DaoRepository.class, fc)) {
                    String daoName = StringUtils.hasText(dao.name()) ? dao.name() : dao.autoRegister()
                            ? String.format("%sDao", convertName(dao.value().getSimpleName())) : null;
                    DaoRepository<?> resultDao = daoName != null && !daoName.isEmpty() ? getBean(daoName, DaoRepository.class) : null;
                    if (resultDao == null) {
                        if (ClassUtils.isAssignable(fc, AbstractSpringDao.class)) {
                            resultDao = new AbstractSpringDao() {

                                private boolean isDebugExport = false;

                                public Class getClazz() {
                                    if (!isDebugExport) {
                                        isDebugExport = true;
                                        logger.debug("{} @Dao annotate field and getClazz() return {}", bean.getClass().getSimpleName(), dao.value().getSimpleName());
                                    }
                                    return dao.value();
                                }
                            };
                            ((JpaDaoSupport) resultDao).setEntityManagerFactory(context.getBean(dao.factory(), EntityManagerFactory.class));
                        } else {
                            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) regContext.getBeanFactory();
                            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
                            beanDefinition.setBeanClass(fc);
                            beanDefinition.setLazyInit(false);
                            beanDefinition.setAbstract(false);
                            beanDefinition.setAutowireCandidate(true);
                            beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
                            String def = "_" + String.valueOf(defcnt.getAndAdd(1));
                            if (ClassUtils.isAssignable(JpaDaoSupport.class, fc)) {
                                beanDefinition.getPropertyValues().add("entityManagerFactory", context.getBean(dao.factory(), EntityManagerFactory.class));
                            }
                            registry.registerBeanDefinition(daoName + def, beanDefinition);
                            resultDao = (DaoRepository<?>) context.getBean(daoName + def);
                        }
                        if (StringUtils.hasText(daoName)) {
                            registerBean(daoName, resultDao);
                        }
                    }
                    ReflectionUtils.makeAccessible(method);
                    try {
                        logger.debug("Inject {}({}) with {}", new Object[]{method.getName(), fc.getSimpleName(), resultDao.getClass()});
                        method.invoke(bean, resultDao);
                    } catch (InvocationTargetException ex) {
                        logger.error(ex.getMessage(), ex);
                    }
                }
                final DaoManager ormm = method.getAnnotation(DaoManager.class);
                if (ormm != null && ormm.value() != null && fc != null && void.class.equals(method.getReturnType())
                        && (ClassUtils.isAssignable(fc, ormm.baseManagerType()) || ClassUtils.isAssignable(ormm.baseManagerType(), fc))) {
                    String mgrName = StringUtils.hasText(ormm.name()) ? ormm.name() : ormm.autoRegister()
                            ? String.format("%sManager", convertName(ormm.value().getSimpleName())) : null;
                    RepositoryManager resultManager = StringUtils.hasText(mgrName) ? getBean(mgrName, RepositoryManager.class) : null;
                    if (resultManager == null) {
                        if (ClassUtils.isAssignable(ormm.baseManagerType(), fc)) {
                            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) regContext.getBeanFactory();
                            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
                            beanDefinition.setBeanClass(fc);
                            beanDefinition.setLazyInit(false);
                            beanDefinition.setAbstract(false);
                            beanDefinition.setAutowireCandidate(true);
                            beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
                            String def = "_" + String.valueOf(defcnt.getAndAdd(1));
                            registry.registerBeanDefinition(mgrName + def, beanDefinition);
                            resultManager = (RepositoryManager) context.getBean(mgrName + def);
                        } else if (ClassUtils.isAssignable(fc, ormm.baseManagerType())) {
                            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) regContext.getBeanFactory();
                            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
                            beanDefinition.setBeanClass(ormm.baseManagerType());
                            beanDefinition.setLazyInit(false);
                            beanDefinition.setAbstract(false);
                            beanDefinition.setAutowireCandidate(true);
                            beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
                            String def = "_" + String.valueOf(defcnt.getAndAdd(1));
                            registry.registerBeanDefinition(mgrName + def, beanDefinition);
                            resultManager = (RepositoryManager) context.getBean(mgrName + def);
                        }
                    }
                    if (resultManager == null) {
                        throw new BeanNotOfRequiredTypeException(mgrName == null || mgrName.isEmpty()
                                ? ClassUtils.getShortName(fc) : mgrName, ormm.baseManagerType(), fc);
                    } else if (resultManager.getDao() == null) {
                        String daoName = StringUtils.hasText(ormm.daoName()) ? ormm.daoName() : ormm.autoRegister()
                                ? String.format("%sDao", convertName(ormm.value().getSimpleName())) : null;
                        DaoRepository<?> resultDao = StringUtils.hasText(daoName) ? getBean(daoName, DaoRepository.class) : null;
                        if (resultDao == null) {
                            resultDao = new AbstractSpringDao() {

                                private boolean isDebugExport = false;

                                public Class getClazz() {
                                    if (!isDebugExport) {
                                        isDebugExport = true;
                                        logger.debug("{} @DaoManager annotate field and getClazz() return {}", bean.getClass().getSimpleName(), ormm.value().getSimpleName());
                                    }
                                    return ormm.value();
                                }
                            };
                            ((JpaDaoSupport) resultDao).setEntityManagerFactory(context.getBean(ormm.factory(), EntityManagerFactory.class));
                            if (StringUtils.hasText(daoName)) {
                                registerBean(daoName, resultDao);
                            }
                        }
                        resultManager.setDao(resultDao);
                    }
                    if (StringUtils.hasText(mgrName)) {
                        registerBean(mgrName, resultManager);
                    }
                    ReflectionUtils.makeAccessible(method);
                    try {
                        logger.debug("Inject {}({}) with {}", new Object[]{method.getName(), fc.getSimpleName(), resultManager.getClass()});
                        method.invoke(bean, resultManager);
                    } catch (InvocationTargetException ex) {
                        logger.error(ex.getMessage(), ex);
                    }
                }
            }
        });
        return true;
    }
}
