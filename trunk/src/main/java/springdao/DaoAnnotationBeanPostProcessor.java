package springdao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicInteger;
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
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import springdao.support.AbstractSpringDao;
import springdao.support.SimpleSpringDao;

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

    /*private void registerBean(String name, Object bean) {
    if (regContext != null) {
    regContext.getBeanFactory().registerSingleton(name, bean);
    logger.debug("register {} with {}->{}", new Object[]{name, bean.getClass(), bean});
    }
    }*/
    private String convertName(String orgName) {
        return String.valueOf(orgName.charAt(0)).toLowerCase() + orgName.substring(1);
    }

    @Override
    public boolean postProcessAfterInstantiation(final Object bean, String beanName) throws BeansException {

        ReflectionUtils.doWithFields(bean.getClass(), new ReflectionUtils.FieldCallback() {

            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                final Dao dao = field.getAnnotation(Dao.class);

                if (dao != null) {
                    Class<?> genericType = dao == null ? null : dao.value().equals(Object.class) ? null : dao.value();
                    if (genericType == null) {
                        if (field.getGenericType() instanceof ParameterizedType) {
                            ParameterizedType pt = (ParameterizedType) field.getGenericType();
                            Type[] typeArguments = pt.getActualTypeArguments();
                            if (typeArguments != null & typeArguments.length > 0) {
                                genericType = (Class<?>) typeArguments[0];
                            }
                        }
                    }
                    if (genericType == null) {
                        throw new IllegalArgumentException("@Dao field should assoicate a Generic ParameterizedType like DaoManager<Type> " + field.getName() + ";");
                    }
                    final Class<?> assoicateType = genericType;
                    String daoName = StringUtils.hasText(dao.name()) ? dao.name() : dao.autoRegister() ? String.format("%sDao", convertName(assoicateType.getSimpleName())) : null;
                    DaoRepository<?> resultDao = daoName != null && !daoName.isEmpty() ? getBean(daoName, DaoRepository.class) : null;
                    if (resultDao == null) {
                        if (ClassUtils.isAssignable(field.getType(), AbstractSpringDao.class)) {
                            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) regContext.getBeanFactory();
                            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
                            beanDefinition.setBeanClass(SimpleSpringDao.class);
                            beanDefinition.setLazyInit(false);
                            beanDefinition.setAbstract(false);
                            beanDefinition.setAutowireCandidate(true);
                            beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
                            beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(0, assoicateType);
                            registry.registerBeanDefinition(daoName, beanDefinition);
                            resultDao = (DaoRepository<?>) context.getBean(daoName);
                        } else if (ClassUtils.isAssignable(DaoRepository.class, field.getType())) {
                            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) regContext.getBeanFactory();
                            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
                            beanDefinition.setBeanClass(field.getType());
                            beanDefinition.setLazyInit(false);
                            beanDefinition.setAbstract(false);
                            beanDefinition.setAutowireCandidate(true);
                            beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
                            registry.registerBeanDefinition(daoName, beanDefinition);
                            resultDao = (DaoRepository<?>) context.getBean(daoName);
                        } else {
                            throw new BeanNotOfRequiredTypeException(field.getName(), DaoRepository.class, field.getType());
                        }
                        if (StringUtils.hasText(daoName)) {
                            logger.debug("Build, inject field and register bean with {}@{}<{}>", new String[]{daoName,
                                        resultDao.getClass().getSimpleName(), assoicateType.getSimpleName()});
                        } else {
                            logger.debug("Build, and inject field with bean {}@{}<{}>", new String[]{daoName, resultDao.getClass().getSimpleName(), assoicateType.getSimpleName()});
                        }
                    }
                    ReflectionUtils.makeAccessible(field);
                    field.set(bean, resultDao);
                }
                final DaoManager ormm = field.getAnnotation(DaoManager.class);
                if (ormm != null) {
                    Class<?> genericType = ormm == null ? null : ormm.value().equals(Object.class) ? null : ormm.value();
                    if (genericType == null) {
                        if (field.getGenericType() instanceof ParameterizedType) {
                            ParameterizedType pt = (ParameterizedType) field.getGenericType();
                            Type[] typeArguments = pt.getActualTypeArguments();
                            if (typeArguments != null & typeArguments.length > 0) {
                                genericType = (Class<?>) typeArguments[0];
                            }
                        }
                    }
                    if (genericType == null) {
                        throw new IllegalArgumentException("@DaoManager field should assoicate a Generic ParameterizedType like RepositoryManager<Type> " + field.getName() + ";");
                    }
                    final Class<?> assoicateType = genericType;
                    String mgrName = StringUtils.hasText(ormm.name()) ? ormm.name() : ormm.autoRegister()
                            ? String.format("%sManager", convertName(assoicateType.getSimpleName())) : null;
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
                                ? String.format("%sDao", convertName(assoicateType.getSimpleName())) : null;
                        DaoRepository<?> resultDao = StringUtils.hasText(daoName) ? getBean(daoName, DaoRepository.class) : null;
                        if (resultDao == null) {
                            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) regContext.getBeanFactory();
                            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
                            beanDefinition.setBeanClass(SimpleSpringDao.class);
                            beanDefinition.setLazyInit(false);
                            beanDefinition.setAbstract(false);
                            beanDefinition.setAutowireCandidate(true);
                            beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
                            beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(0, assoicateType);
                            registry.registerBeanDefinition(daoName, beanDefinition);
                            resultDao = (DaoRepository<?>) context.getBean(daoName);
                        }
                        resultManager.setDao(resultDao);
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
                if (dao != null && fc != null && void.class.equals(method.getReturnType())
                        && ClassUtils.isAssignable(DaoRepository.class, fc)) {
                    Class<?> genericType = dao == null ? null : dao.value().equals(Object.class) ? null : dao.value();
                    if (genericType == null) {
                        Type[] genericParameterTypes = method.getGenericParameterTypes();
                        if (genericParameterTypes[0] instanceof ParameterizedType) {
                            ParameterizedType aType = (ParameterizedType) genericParameterTypes[0];
                            Type[] parameterArgTypes = aType.getActualTypeArguments();
                            if (parameterArgTypes.length == 1) {
                                genericType = (Class<?>) parameterArgTypes[0];
                            }
                        }
                    }
                    if (genericType == null) {
                        throw new IllegalArgumentException("@Dao Method should assoicate a Generic ParameterizedType like " + method.getName() + "(DaoRepository<Type>)");
                    }
                    final Class<?> assoicateType = genericType;
                    String daoName = StringUtils.hasText(dao.name()) ? dao.name() : dao.autoRegister()
                            ? String.format("%sDao", convertName(assoicateType.getSimpleName())) : null;
                    DaoRepository<?> resultDao = daoName != null && !daoName.isEmpty() ? getBean(daoName, DaoRepository.class) : null;
                    if (resultDao == null) {
                        if (ClassUtils.isAssignable(fc, AbstractSpringDao.class)) {
                            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) regContext.getBeanFactory();
                            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
                            beanDefinition.setBeanClass(SimpleSpringDao.class);
                            beanDefinition.setLazyInit(false);
                            beanDefinition.setAbstract(false);
                            beanDefinition.setAutowireCandidate(true);
                            beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
                            beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(0, assoicateType);
                            registry.registerBeanDefinition(daoName, beanDefinition);
                            resultDao = (DaoRepository<?>) context.getBean(daoName);
                        } else {
                            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) regContext.getBeanFactory();
                            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
                            beanDefinition.setBeanClass(fc);
                            beanDefinition.setLazyInit(false);
                            beanDefinition.setAbstract(false);
                            beanDefinition.setAutowireCandidate(true);
                            beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
                            String def = "_" + String.valueOf(defcnt.getAndAdd(1));
                            registry.registerBeanDefinition(daoName, beanDefinition);
                            resultDao = (DaoRepository<?>) context.getBean(daoName);
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
                if (ormm != null && fc != null && void.class.equals(method.getReturnType())
                        && (ClassUtils.isAssignable(fc, ormm.baseManagerType()) || ClassUtils.isAssignable(ormm.baseManagerType(), fc))) {
                    Class<?> genericType = ormm == null ? null : ormm.value().equals(Object.class) ? null : ormm.value();
                    if (genericType == null) {
                        Type[] genericParameterTypes = method.getGenericParameterTypes();
                        if (genericParameterTypes[0] instanceof ParameterizedType) {
                            ParameterizedType aType = (ParameterizedType) genericParameterTypes[0];
                            Type[] parameterArgTypes = aType.getActualTypeArguments();
                            if (parameterArgTypes.length == 1) {
                                genericType = (Class<?>) parameterArgTypes[0];
                            }
                        }
                    }
                    if (genericType == null) {
                        throw new IllegalArgumentException("@DaoMethod Method should assoicate a Generic ParameterizedType like " + method.getName() + "(RepositoryManager<Type>)");
                    }
                    final Class<?> assoicateType = genericType;
                    String mgrName = StringUtils.hasText(ormm.name()) ? ormm.name() : ormm.autoRegister()
                            ? String.format("%sManager", convertName(assoicateType.getSimpleName())) : null;
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
                                ? String.format("%sDao", convertName(assoicateType.getSimpleName())) : null;
                        DaoRepository<?> resultDao = StringUtils.hasText(daoName) ? getBean(daoName, DaoRepository.class) : null;
                        if (resultDao == null) {
                            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) regContext.getBeanFactory();
                            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
                            beanDefinition.setBeanClass(SimpleSpringDao.class);
                            beanDefinition.setLazyInit(false);
                            beanDefinition.setAbstract(false);
                            beanDefinition.setAutowireCandidate(true);
                            beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
                            beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(0, assoicateType);
                            registry.registerBeanDefinition(daoName, beanDefinition);
                            resultDao = (DaoRepository<?>) context.getBean(daoName);
                        }
                        resultManager.setDao(resultDao);
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
