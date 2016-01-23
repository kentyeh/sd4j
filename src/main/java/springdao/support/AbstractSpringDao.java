package springdao.support;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.Query;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.dao.support.DaoSupport;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import springdao.DaoRepository;

/**
 *
 * @author Kent Yeh
 * @param <E>
 */
public abstract class AbstractSpringDao<E> extends DaoSupport implements DaoRepository<E> {

    private static final Logger log = LogManager.getLogger(AbstractSpringDao.class);
    private EntityManagerFactory emf;
    private EntityManager em;

    @Override
    protected void checkDaoConfig() throws IllegalArgumentException {
        if (this.emf == null) {
            throw new IllegalArgumentException("'EntityManagerFactory' is required");
        }
        if (this.em == null) {
            throw new IllegalArgumentException("'EntityManager' is required");
        }
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    @PersistenceUnit
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return em;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    LockModeType getLockMode(String lockMode) {
        try {
            return LockModeType.valueOf(lockMode);
        } catch (RuntimeException ex) {
            return LockModeType.NONE;
        }
    }

    @Override
    public E instanate() throws InstantiationException, IllegalAccessException {
        try {
            return getClazz().newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            log.error("Instanate error", ex);
            throw ex;
        }
    }

    private RuntimeException convertException(Exception e) {
        if (e instanceof RuntimeException) {
            RuntimeException res = EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible((RuntimeException) e);
            return res == null ? (RuntimeException) e : res;
        } else {
            return new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void clear() {
        try {
            em.clear();
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    @Override
    public boolean contains(Object entity) {
        try {
            return em.contains(entity);
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    @Override
    public E findByPrimaryKey(Serializable primaryKey) {
        try {
            return em.find(getClazz(), primaryKey);
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    @Override
    public E findByPrimaryKey(Serializable primaryKey, String lockMode) {
        try {
            return em.find(getClazz(), primaryKey, getLockMode(lockMode));
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    @Override
    public E findByPrimaryKey(Serializable primaryKey, Map<String, Object> properties) {
        try {
            return em.find(getClazz(), primaryKey, properties);
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    @Override
    public E findByPrimaryKey(Serializable primaryKey, String lockMode, Map<String, Object> properties) {
        try {
            return em.find(getClazz(), primaryKey, getLockMode(lockMode), properties);
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    @Override
    public E save(E entity) {
        return persist(entity);
    }

    @Override
    public Collection<E> save(Collection<E> entities) {
        try {
            ArrayList<E> result = new ArrayList<>(entities.size());
            for (E entity : entities) {
                result.add(persist(entity));
            }
            em.flush();
            return result;
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    @Override
    public E persist(E entity) {
        try {
            em.persist(entity);
            em.flush();
            return entity;
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    @Override
    public E update(E entity) {
        return merge(entity);
    }

    @Override
    public E update(E entity, String lockMode) {
        try {
            E result = em.merge(entity);
            em.flush();
            em.lock(em, getLockMode(lockMode));
            return result;
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    @Override
    public Collection<E> update(Collection<E> entities) {
        return merge(entities);
    }

    @Override
    public E merge(E entity) {
        try {
            E result = em.merge(entity);
            em.flush();
            return result;
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    @Override
    public Collection<E> merge(Collection<E> entities) {
        try {
            ArrayList<E> result = new ArrayList<>(entities.size());
            for (E entity : entities) {
                result.add(em.merge(entity));
            }
            em.flush();
            return result;
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    /**
     * Before using this method, look at
     * <a
     * href="http://blog.xebia.com/2009/03/23/jpa-implementation-patterns-saving-detached-entities/">saveOrUpdate
     * vs. merge</a>.
     *
     * @param entity
     * @return merged entity
     */
    @Override
    public E saveOrUpdate(E entity) {
        return contains(entity) ? merge(entity) : emf.getPersistenceUnitUtil().getIdentifier(entity) == null ? persist(entity) : merge(entity);
    }

    @Override
    public Collection<E> saveOrUpdate(Collection<E> entities) {
        try {
            ArrayList<E> result = new ArrayList<>(entities.size());
            for (E entity : entities) {
                result.add(saveOrUpdate(entity));
            }
            em.flush();
            return result;
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    @Override
    public void delete(Serializable primaryKey) {
        try {
            Object entity = em.find(getClazz(), primaryKey);
            if (entity != null) {
                em.remove(entity);
                em.flush();
            }
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    @Override
    public void delete(Serializable primaryKey, String lockMode) {
        try {
            Object entity = em.find(getClazz(), primaryKey);
            if (entity != null) {
                em.lock(entity, getLockMode(lockMode));
                em.remove(entity);
                em.flush();
            }
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    @Override
    public void delete(Collection<? extends Serializable> primaryKeys) {
        try {
            for (Serializable pk : primaryKeys) {
                Object entity = em.find(getClazz(), pk);
                if (entity != null) {
                    em.remove(entity);
                }
            }
            em.flush();
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    @Override
    public E remove(E entity) {
        entity = merge(entity);
        try {
            em.remove(entity);
            em.flush();
            return entity;
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    @Override
    public E remove(E entity, String lockMode) {
        em.lock(entity, getLockMode(lockMode));
        em.remove(entity);
        em.flush();
        return entity;
    }

    @Override
    public Collection<E> remove(Collection<E> entities) {
        try {
            ArrayList<E> result = new ArrayList<>(entities.size());
            for (E entity : entities) {
                entity = merge(entity);
                em.remove(entity);
                result.add(entity);
            }
            em.flush();
            return result;
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    @Override
    public E lock(E entity, String lockMode) {
        try {
            em.lock(entity, getLockMode(lockMode));
            return entity;
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    @Override
    public E refresh(E entity) {
        try {
            em.refresh(entity);
            return entity;
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    @Override
    public E refresh(E entity, String lockMode) {
        try {
            em.refresh(entity, getLockMode(lockMode));
            return entity;
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    @Override
    public int bulkUpdate(String QL) {
        try {
            return em.createQuery(QL).executeUpdate();
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    @Override
    public List<Integer> bulkUpdate(List<String> QLs) {
        try {
            List<Integer> result = new ArrayList<>();
            for (String ql : QLs) {
                result.add(em.createQuery(ql).executeUpdate());
            }
            return result;
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    @Override
    public int bulkUpdate(String QL, Object... parameters) {
        try {
            Query query = em.createQuery(QL);
            int i = 0;
            if (parameters != null && parameters.length > 0) {
                for (Object paramter : parameters) {
                    query.setParameter(++i, paramter);
                }
            }
            return query.executeUpdate();
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    @Override
    public int bulkUpdate(String QL, Map<String, ?> parameters) {
        try {
            Query query = em.createQuery(QL);
            int i = 0;
            if (parameters != null && !parameters.isEmpty()) {
                for (String key : parameters.keySet()) {
                    query.setParameter(key, parameters.get(key));
                }
            }
            return query.executeUpdate();
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    @Override
    public String $e() {
        return getEntityName();
    }

    @Override
    public String getEntityName() {
        return getClazz().getName();
    }

    @Override
    public String $a() {
        return getAliasName();
    }

    @Override
    public String $ea() {
        return getEntityName() + " AS " + getAliasName();
    }

    @Override
    public String getAliasName() {
        return AliasHelper.$a(getClazz());
    }

    protected List<E> findList(Query query) {
        try {
            EntityManagerFactoryUtils.applyTransactionTimeout(query, getEntityManagerFactory());
            return query.getResultList();
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    protected List<E> findList(Query query, Object... parameters) {
        try {
            EntityManagerFactoryUtils.applyTransactionTimeout(query, getEntityManagerFactory());
            if (parameters != null && parameters.length > 0) {
                for (int i = 0; i < parameters.length; i++) {
                    query.setParameter(i + 1, parameters[i]);
                }
            }
            return query.getResultList();
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    protected List<E> findList(Query query, Map<String, ?> parameters) {
        try {
            EntityManagerFactoryUtils.applyTransactionTimeout(query, getEntityManagerFactory());
            if (parameters != null && !parameters.isEmpty()) {
                for (Map.Entry<String, ?> entry : parameters.entrySet()) {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }
            return query.getResultList();
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    @Override
    public List<E> findByCriteria(String qlCriteria) {
        return findList(em.createQuery(JpqlHelper.get().select($a()).
                from($ea()).$(qlCriteria).ql()));
    }

    @Override
    public List<E> findByCriteria(String qlCriteria, Object... parameters) {
        return findList(em.createQuery(JpqlHelper.get().select($a()).
                from($ea()).$(qlCriteria).ql()), parameters);
    }

    @Override
    public List<E> findByCriteria(String qlCriteria, Map<String, ?> parameters) {
        return findList(em.createQuery(JpqlHelper.get().select($a()).from($ea())
                .$(qlCriteria).ql()), parameters);
    }

    @Override
    public List<E> findByCriteria(String qlCriteria, int startPageNo, int pageSize, Object... parameters) {
        if ((startPageNo < 1) || (pageSize < 1)) {
            return parameters == null || parameters.length == 0 ? findByCriteria(qlCriteria) : findByCriteria(qlCriteria, parameters);
        } else if (parameters == null || parameters.length == 0) {
            return findByCriteria(qlCriteria, startPageNo, pageSize);
        } else {
            return findList(em.createQuery(JpqlHelper.get().select($a()).from($ea()).$(qlCriteria).ql()).
                    setFirstResult((startPageNo - 1) * pageSize).setMaxResults(pageSize), parameters);
        }
    }

    @Override
    public List<E> findByCriteria(String qlCriteria, int startPageNo, int pageSize, Map<String, ?> parameters) {
        if ((startPageNo < 1) || (pageSize < 1)) {
            return parameters == null || parameters.isEmpty() ? findByCriteria(qlCriteria) : findByCriteria(qlCriteria, parameters);
        } else if (parameters == null || parameters.isEmpty()) {
            return findByCriteria(qlCriteria, startPageNo, pageSize);
        } else {
            return findList(em.createQuery(JpqlHelper.get().select($a()).from($ea()).$(qlCriteria).ql())
                    .setFirstResult((startPageNo - 1) * pageSize).setMaxResults(pageSize), parameters);
        }
    }

    @Override
    public List<E> findByCriteria(String qlCriteria, int startPageNo, int pageSize) {
        if ((startPageNo < 1) || (pageSize < 1)) {
            return findByCriteria(qlCriteria);
        } else {
            return findList(em.createQuery(JpqlHelper.get().select($a()).from($ea()).$(qlCriteria).ql()).setFirstResult((startPageNo - 1) * pageSize).setMaxResults(pageSize));
        }
    }

    @Override
    public List<E> findBySQLQuery(String sql) {
        return findList(em.createNativeQuery(sql, getClazz()));
    }

    @Override
    public List<E> findBySQLQuery(String sql, Object... parameters) {
        return findList(em.createNativeQuery(sql, getClazz()), parameters);
    }

    @Override
    public List<E> findBySQLQuery(String sql, Map<String, ?> parameters) {
        return findList(em.createNativeQuery(sql, getClazz()), parameters);
    }

    @Override
    public <T> T findUniqueByQL(String QL) {
        try {
            Query query = em.createQuery(QL);
            EntityManagerFactoryUtils.applyTransactionTimeout(query, getEntityManagerFactory());
            return (T) query.getSingleResult();
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    @Override
    public <T> T findUniqueByQL(Class<T> clazz, String QL) {
        return findUniqueByQL(QL);
    }

    @Override
    public <T> T findUniqueByQL(String QL, Object... parameters) {
        try {
            Query query = em.createQuery(QL);
            EntityManagerFactoryUtils.applyTransactionTimeout(query, getEntityManagerFactory());
            if (parameters != null && parameters.length > 0) {
                for (int i = 0; i < parameters.length; i++) {
                    query.setParameter(i + 1, parameters[i]);
                }
            }
            return (T) query.getSingleResult();
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    @Override
    public <T> T findUniqueByQL(Class<T> clazz, String QL, Object... parameters) {
        return findUniqueByQL(QL, parameters);
    }

    @Override
    public <T> T findUniqueByQL(String QL, Map<String, ?> parameters) {
        try {
            Query query = em.createQuery(QL);
            EntityManagerFactoryUtils.applyTransactionTimeout(query, getEntityManagerFactory());
            if (parameters != null && !parameters.isEmpty()) {
                for (Map.Entry<String, ?> entry : parameters.entrySet()) {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }
            return (T) query.getSingleResult();
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    @Override
    public <T> T findUniqueByQL(Class<T> clazz, String QL, Map<String, ?> parameters) {
        return findUniqueByQL(QL, parameters);
    }

    @Override
    public <T> List<T> findListByQL(String QL) {
        try {
            assert em != null : "*************** em is empty******************";
            Query query = em.createQuery(QL);
            EntityManagerFactoryUtils.applyTransactionTimeout(query, getEntityManagerFactory());
            return query.getResultList();
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
            throw convertException(e);
        }
    }

    @Override
    public <T> List<T> findListByQL(Class<T> clazz, String QL) {
        return findListByQL(QL);
    }

    @Override
    public <T> List<T> findListByQL(String QL, Object... parameters) {
        try {
            Query query = em.createQuery(QL);
            EntityManagerFactoryUtils.applyTransactionTimeout(query, getEntityManagerFactory());
            if (parameters != null && parameters.length > 0) {
                for (int i = 0; i < parameters.length; i++) {
                    query.setParameter(i + 1, parameters[i]);
                }
            }
            return query.getResultList();
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    @Override
    public <T> List<T> findListByQL(Class<T> clazz, String QL, Object... parameters) {
        return findListByQL(QL, parameters);
    }

    @Override
    public <T> List<T> findListByQL(String QL, Map<String, ?> parameters) {
        try {
            Query query = em.createQuery(QL);
            EntityManagerFactoryUtils.applyTransactionTimeout(query, getEntityManagerFactory());
            if (parameters != null && !parameters.isEmpty()) {
                for (Map.Entry<String, ?> entry : parameters.entrySet()) {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }
            return query.getResultList();
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    @Override
    public <T> List<T> findListByQL(Class<T> clazz, String QL, Map<String, ?> parameters) {
        return findListByQL(QL, parameters);
    }

    @Override
    public List<E> findByNamedQuery(String name) {
        return findList(em.createNamedQuery(name, getClazz()));
    }

    @Override
    public List<E> findByNamedQuery(String name, Object... parameters) {
        return findList(em.createNamedQuery(name, getClazz()), parameters);
    }

    @Override
    public List<E> findByNamedQuery(String name, Map<String, ?> parameters) {
        return findList(em.createNamedQuery(name, getClazz()), parameters);
    }

    @Override
    public <T> List<T> findListByNamedQuery(String name) {
        try {
            Query query = em.createNamedQuery(name);
            EntityManagerFactoryUtils.applyTransactionTimeout(query, getEntityManagerFactory());
            return query.getResultList();
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    @Override
    public <T> List<T> findListByNamedQuery(Class<T> clazz, String name) {
        return findListByNamedQuery(name);
    }

    @Override
    public <T> List<T> findListByNamedQuery(String name, Object... parameters) {
        try {
            Query query = em.createNamedQuery(name);
            EntityManagerFactoryUtils.applyTransactionTimeout(query, getEntityManagerFactory());
            if (parameters != null && parameters.length > 0) {
                for (int i = 0; i < parameters.length; i++) {
                    query.setParameter(i + 1, parameters[i]);
                }
            }
            return query.getResultList();
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    @Override
    public <T> List<T> findListByNamedQuery(Class<T> clazz, String name, Object... parameters) {
        return findListByNamedQuery(name, parameters);
    }

    @Override
    public <T> List<T> findListByNamedQuery(String name, Map<String, ?> parameters) {
        try {
            Query query = em.createNamedQuery(name);
            EntityManagerFactoryUtils.applyTransactionTimeout(query, getEntityManagerFactory());
            if (parameters != null && !parameters.isEmpty()) {
                for (Map.Entry<String, ?> entry : parameters.entrySet()) {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }
            return query.getResultList();
        } catch (RuntimeException e) {
            throw convertException(e);
        }
    }

    @Override
    public <T> List<T> findListByNamedQuery(Class<T> clazz, String name, Map<String, ?> parameters) {
        return findListByNamedQuery(name, parameters);
    }

    @Override
    public E initLazyCollection(E entity, final String collectionFieldName) {
        final AtomicBoolean found = new AtomicBoolean(false);
        final String methodName = collectionFieldName.matches("^[a-z][A-Z]") ? collectionFieldName : collectionFieldName.length() > 1
                ? collectionFieldName.substring(0, 1).toUpperCase() + collectionFieldName.substring(1) : collectionFieldName.toUpperCase();
        final Object obj = entity;
        final EntityManager fem = em;
        try {
            ReflectionUtils.doWithMethods(getClazz(),
                    new ReflectionUtils.MethodCallback() {
                        @Override
                        public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
                            try {
                                Method setter = obj.getClass().getMethod("s" + method.getName().substring(1), method.getReturnType());
                                PersistenceUnitUtil puu = fem.getEntityManagerFactory().getPersistenceUnitUtil();
                                if (!puu.isLoaded(obj, collectionFieldName)) {
                                    E reattach = (E) fem.merge(obj);
//                                    E reattach = (E) em.find(obj.getClass(), puu.getIdentifier(obj));
                                    Object fieldObj = method.invoke(reattach, new Object[]{});
                                    ((Collection) fieldObj).size();
                                    setter.invoke(obj, fieldObj);
                                }
                            } catch (NoSuchMethodException ex) {
                                throw new PersistenceException("Setter " + getClazz().getSimpleName() + ".set" + methodName + "(...) not found.", ex);
                            } catch (InvocationTargetException ex) {
                                throw new PersistenceException("Could not fetch Collection from " + getClazz().getSimpleName() + "." + method.getName(), ex);
                            }
                        }
                    },
                    new ReflectionUtils.MethodFilter() {
                        @Override
                        public boolean matches(Method method) {
                            if (found.get()) {
                                return false;
                            } else {
                                found.set(method.getName().equals("get" + methodName) && method.getParameterTypes().length == 0
                                        && ClassUtils.isAssignable(Collection.class, method.getReturnType()));
                                return found.get();
                            }
                        }
                    });
            return (E) obj;
        } catch (IllegalArgumentException e) {
            throw convertException(e);
        } finally {
            EntityManagerFactoryUtils.closeEntityManager(fem);
        }
    }
}
