package springdao.reposotory;

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
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.support.DaoSupport;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import springdao.support.AliasHelper;
import springdao.DaoRepository;
import springdao.model.Member;

/**
 *
 * @author Kent Yeh
 */
@Log4j2
public class AnnotherDaoRepository extends DaoSupport implements DaoRepository<Member> {

    private @Getter
    EntityManagerFactory entityManagerFactory;
    private @Getter
    EntityManager entityManager;

    @Override
    public Class<Member> getClazz() {
        return Member.class;
    }

    @Override
    protected void checkDaoConfig() throws IllegalArgumentException {
        if (this.entityManagerFactory == null) {
            throw new IllegalArgumentException("'EntityManagerFactory' is required");
        }
    }

    @PersistenceUnit
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.entityManagerFactory = emf;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.entityManager = em;
    }

    private LockModeType getLockMode(String lockMode) {
        try {
            return LockModeType.valueOf(lockMode);
        } catch (RuntimeException ex) {
            return LockModeType.NONE;
        }
    }

    @Override
    public Member instanate() throws InstantiationException, IllegalAccessException {
        try {
            return getClazz().newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            logger.error("Instanate error", ex);
            throw ex;
        }
    }

    @Override
    public void clear() {
        try {
            entityManager.clear();
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        }
    }

    @Override
    public boolean contains(Object entity) {
        try {
            return entityManager.contains(entity);
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        }
    }

    @Override
    public Member findByPrimaryKey(Serializable primaryKey) {
        try {
            return entityManager.find(getClazz(), primaryKey);
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        }
    }

    @Override
    public Member findByPrimaryKey(Serializable primaryKey, String lockMode) {
        try {
            return entityManager.find(getClazz(), primaryKey, getLockMode(lockMode));
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        }
    }

    @Override
    public Member findByPrimaryKey(Serializable primaryKey, Map<String, Object> properties) {
        try {
            return entityManager.find(getClazz(), primaryKey, properties);
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        }
    }

    @Override
    public Member findByPrimaryKey(Serializable primaryKey, String lockMode, Map<String, Object> properties) {
        try {
            return entityManager.find(getClazz(), primaryKey, getLockMode(lockMode), properties);
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        }
    }

    @Override
    public Member save(Member entity) {
        return contains(entity) ? merge(entity) : persist(entity);
    }

    @Override
    public Collection<Member> save(Collection<Member> entities) {
        try {
            ArrayList<Member> result = new ArrayList<>(entities.size());
            for (Member entity : entities) {
                if (entityManager.contains(entity)) {
                    result.add(entityManager.merge(entity));
                } else {
                    entityManager.persist(entity);
                    result.add(entity);
                }
            }
            return result;
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        }
    }

    @Override
    public Member persist(Member entity) {
        try {
            entityManager.persist(entity);
            entityManager.flush();
            return entity;
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        }
    }

    @Override
    public Member update(Member entity) {
        return merge(entity);
    }

    @Override
    public Member update(Member entity, String lockMode) {
        try {
            Member result = entityManager.merge(entity);
            entityManager.flush();
            entityManager.lock(entityManager, getLockMode(lockMode));
            return result;
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        }
    }

    @Override
    public Collection<Member> update(Collection<Member> entities) {
        return merge(entities);
    }

    @Override
    public Member merge(Member entity) {
        try {
            Member result = entityManager.merge(entity);
            entityManager.flush();
            return result;
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        }
    }

    @Override
    public Collection<Member> merge(Collection<Member> entities) {
        try {
            ArrayList<Member> result = new ArrayList<>(entities.size());
            for (Member entity : entities) {
                result.add(entityManager.merge(entity));
            }
            entityManager.flush();
            return result;
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        }
    }

    @Override
    public Member merge(String entityName, Member entity) {
        return merge(entity);
    }

    /**
     * Before using this method, look at
     * <a
     * href="http://blog.xebia.com/2009/03/23/jpa-implementation-patterns-saving-detached-entities/">saveOrUpdate
     * vs. merge</a>.
     *
     * @param entity
     * @return
     */
    @Override
    public Member saveOrUpdate(Member entity) {
        return merge(entity);
    }

    @Override
    public Member saveOrUpdate(String entityName, Member entity) {
        return merge(entity);
    }

    @Override
    public Collection<Member> saveOrUpdate(Collection<Member> entities) {
        return merge(entities);
    }

    @Override
    public void delete(Serializable pk) {
        try {
            Member m = entityManager.find(Member.class, pk);
            if (m != null) {
                entityManager.remove(m);
                entityManager.flush();
            }
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        }
    }

    @Override
    public void delete(Serializable pk, String lockMode) {
        try {
            Member m = entityManager.find(Member.class, pk);
            if (m != null) {
                entityManager.lock(m, getLockMode(lockMode));
                entityManager.remove(m);
                entityManager.flush();
            }
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        }
    }

    @Override
    public void delete(Collection<Serializable> pks) {
        try {
            for (Serializable pk : pks) {
                Member m = entityManager.find(Member.class, pk);
                if (m != null) {
                    entityManager.remove(m);
                }
            }
            entityManager.flush();
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        }
    }

    @Override
    public Member lock(Member entity, String lockMode) {
        try {
            entityManager.lock(entity, getLockMode(lockMode));
            return entity;
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        }
    }

    @Override
    public Member refresh(Member entity) {
        try {
            entityManager.refresh(entity);
            return entity;
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        }
    }

    @Override
    public Member refresh(Member entity, String lockMode) {
        try {
            entityManager.refresh(entity, getLockMode(lockMode));
            return entity;
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        }
    }

    @Override
    public int bulkUpdate(String QL) {
        try {
            return entityManager.createQuery(QL).executeUpdate();
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        }
    }

    @Override
    public List<Integer> bulkUpdate(List<String> QLs) {
        try {
            List<Integer> result = new ArrayList<>();
            for (String ql : QLs) {
                result.add(entityManager.createQuery(ql).executeUpdate());
            }
            return result;
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        }
    }

    @Override
    public int bulkUpdate(String QL, Object... parameters) {
        try {
            Query query = entityManager.createQuery(QL);
            int i = 0;
            if (parameters != null && parameters.length > 0) {
                for (Object paramter : parameters) {
                    query.setParameter(++i, paramter);
                }
            }
            return query.executeUpdate();
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        }
    }

    @Override
    public int bulkUpdate(String QL, Map<String, ?> parameters) {
        try {
            Query query = entityManager.createQuery(QL);
            int i = 0;
            if (parameters != null && !parameters.isEmpty()) {
                for (String key : parameters.keySet()) {
                    query.setParameter(key, parameters.get(key));
                }
            }
            return query.executeUpdate();
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        }
    }

    @Override
    public String getEntityName() {
        return getClazz().getName();
    }

    @Override
    public String $e() {
        return getEntityName();
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
        return AliasHelper.getAlias(getClazz());
    }

    protected List<Member> findList(Query query) {
        try {
            EntityManagerFactoryUtils.applyTransactionTimeout(query, getEntityManagerFactory());
            return query.getResultList();
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        }
    }

    protected List<Member> findList(Query query, Object... parameters) {
        try {
            EntityManagerFactoryUtils.applyTransactionTimeout(query, getEntityManagerFactory());
            if (parameters != null && parameters.length > 0) {
                for (int i = 0; i < parameters.length; i++) {
                    query.setParameter(i + 1, parameters[i]);
                }
            }
            return query.getResultList();
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        }
    }

    protected List<Member> findList(Query query, Map<String, ?> parameters) {
        try {
            EntityManagerFactoryUtils.applyTransactionTimeout(query, getEntityManagerFactory());
            if (parameters != null && !parameters.isEmpty()) {
                for (Map.Entry<String, ?> entry : parameters.entrySet()) {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }
            return query.getResultList();
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        }
    }

    @Override
    public List<Member> findByCriteria(String qlCriteria) {
        StringBuilder sb = new StringBuilder("SELECT ").append(getAliasName()).append(" FROM ").
                append(getEntityName()).append("  ").append(getAliasName()).append(" ").append(qlCriteria);
        return findList(entityManager.createQuery(sb.toString()));
    }

    @Override
    public List<Member> findByCriteria(String qlCriteria, Object... parameters) {
        StringBuilder sb = new StringBuilder("SELECT ").append(getAliasName()).append(" FROM ").
                append(getEntityName()).append("  ").append(getAliasName()).append(" ").append(qlCriteria);
        return findList(entityManager.createQuery(sb.toString()), parameters);
    }

    @Override
    public List<Member> findByCriteria(String qlCriteria, Map<String, ?> parameters) {
        StringBuilder sb = new StringBuilder("SELECT ").append(getAliasName()).append(" FROM ").
                append(getEntityName()).append("  ").append(getAliasName()).append(" ").append(qlCriteria);
        return findList(entityManager.createQuery(sb.toString()), parameters);
    }

    @Override
    public List<Member> findByJoinCriteria(String joins, String qlCriteria) {
        StringBuilder sb = new StringBuilder("SELECT DISTINCT ").append(getAliasName()).
                append(" FROM ").append(getEntityName()).append(" ").append(getAliasName()).append(" JOIN ").
                append(joins).append(" ").append(qlCriteria);
        return findList(entityManager.createQuery(sb.toString()));
    }

    @Override
    public List<Member> findByJoinCriteria(String joins, String qlCriteria, Object... parameters) {
        StringBuilder sb = new StringBuilder("SELECT DISTINCT ").append(getAliasName()).
                append(" FROM ").append(getEntityName()).append(" ").append(getAliasName()).append(" ").
                append(joins).append(" ").append(qlCriteria);
        return findList(entityManager.createQuery(sb.toString()));
    }

    @Override
    public List<Member> findByJoinCriteria(String joins, String qlCriteria, Map<String, ?> parameters) {
        StringBuilder sb = new StringBuilder("SELECT DISTINCT ").append(getAliasName()).
                append(" FROM ").append(getEntityName()).append(" ").append(getAliasName()).append(" ").
                append(joins).append(" ").append(qlCriteria);
        return findList(entityManager.createQuery(sb.toString()), parameters);
    }

    @Override
    public List<Member> findByCriteria(String qlCriteria, int startPageNo, int pageSize, Object... parameters) {
        if ((startPageNo < 1) || (pageSize < 1)) {
            return parameters != null || parameters.length == 0 ? findByCriteria(qlCriteria) : findByCriteria(qlCriteria, parameters);
        } else if (parameters == null || parameters.length == 0) {
            return findByCriteria(qlCriteria, startPageNo, pageSize);
        } else {
            StringBuilder sb = new StringBuilder("SELECT ").append(getAliasName()).append(" FROM ").
                    append(getEntityName()).append("  ").append(getAliasName()).append(" ").append(qlCriteria);
            return findList(entityManager.createQuery(sb.toString()).
                    setFirstResult((startPageNo - 1) * pageSize).setMaxResults(pageSize), parameters);
        }
    }

    @Override
    public List<Member> findByCriteria(String qlCriteria, int startPageNo, int pageSize, Map<String, ?> parameters) {
        if ((startPageNo < 1) || (pageSize < 1)) {
            return parameters == null || parameters.isEmpty() ? findByCriteria(qlCriteria) : findByCriteria(qlCriteria, parameters);
        } else if (parameters == null || parameters.isEmpty()) {
            return findByCriteria(qlCriteria, startPageNo, pageSize);
        } else {
            StringBuilder sb = new StringBuilder("SELECT ").append(getAliasName()).append(" FROM ").
                    append(getEntityName()).append("  ").append(getAliasName()).append(" ").append(qlCriteria);
            return findList(entityManager.createQuery(sb.toString()).setFirstResult((startPageNo - 1) * pageSize).setMaxResults(pageSize), parameters);
        }
    }

    @Override
    public List<Member> findByJoinCriteria(String joins, String qlCriteria, int startPageNo, int pageSize,
            Object... parameters) {
        if ((startPageNo < 1) || (pageSize < 1)) {
            return parameters == null || parameters.length == 0 ? findByJoinCriteria(joins, qlCriteria)
                    : findByJoinCriteria(joins, qlCriteria, parameters);
        } else if (parameters == null || parameters.length == 0) {
            return findByCriteria(qlCriteria, startPageNo, pageSize);
        } else {
            StringBuilder sb = new StringBuilder("SELECT DISTINCT ").append(getAliasName()).
                    append(" FROM ").append(getEntityName()).append(" ").append(getAliasName()).append(" ").
                    append(joins).append(" ").append(qlCriteria);
            return findList(entityManager.createQuery(sb.toString()).
                    setFirstResult((startPageNo - 1) * pageSize).setMaxResults(pageSize), parameters);
        }
    }

    @Override
    public List<Member> findByJoinCriteria(String joins, String qlCriteria, int startPageNo, int pageSize,
            Map<String, ?> parameters) {
        if ((startPageNo < 1) || (pageSize < 1)) {
            return parameters == null || parameters.isEmpty() ? findByJoinCriteria(joins, qlCriteria)
                    : findByJoinCriteria(joins, qlCriteria, parameters);
        } else if (parameters == null || parameters.isEmpty()) {
            return findByCriteria(qlCriteria, startPageNo, pageSize);
        } else {
            StringBuilder sb = new StringBuilder("SELECT DISTINCT ").append(getAliasName()).
                    append(" FROM ").append(getEntityName()).append(" ").append(getAliasName()).append(" ").
                    append(joins).append(" ").append(qlCriteria);
            return findList(entityManager.createQuery(sb.toString()).
                    setFirstResult((startPageNo - 1) * pageSize).setMaxResults(pageSize), parameters);
        }
    }

    @Override
    public List<Member> findByCriteria(String qlCriteria, int startPageNo, int pageSize) {
        if ((startPageNo < 1) || (pageSize < 1)) {
            return findByCriteria(qlCriteria);
        } else {
            StringBuilder sb = new StringBuilder("SELECT ").append(getAliasName()).append(" FROM ").
                    append(getEntityName()).append("  ").append(getAliasName()).append(" ").append(qlCriteria);
            return findList(entityManager.createQuery(sb.toString()).setFirstResult((startPageNo - 1) * pageSize).setMaxResults(pageSize));
        }
    }

    @Override
    public List<Member> findByJoinCriteria(String joins, final String qlCriteria, final int startPageNo, final int pageSize) {
        final StringBuilder sb = new StringBuilder("SELECT DISTINCT ").append(getAliasName()).
                append(" FROM ").append(getEntityName()).append(" ").append(getAliasName()).append(" ").
                append(joins).append(" ").append(qlCriteria);
        if ((startPageNo < 1) || (pageSize < 1)) {
            return findList(entityManager.createQuery(sb.toString()));
        } else {
            return findList(entityManager.createQuery(sb.toString()).setFirstResult((startPageNo - 1) * pageSize).setMaxResults(pageSize));
        }
    }

    @Override
    public List<Member> findBySQLQuery(String sql) {
        return findList(entityManager.createNativeQuery(sql, getClazz()));
    }

    @Override
    public List<Member> findBySQLQuery(String sql, Object... parameters) {
        return findList(entityManager.createNativeQuery(sql, getClazz()), parameters);
    }

    @Override
    public List<Member> findBySQLQuery(String sql, Map<String, ?> parameters) {
        return findList(entityManager.createNativeQuery(sql, getClazz()), parameters);
    }

    @Override
    public <T> T findUniqueByQL(String QL) {
        try {
            Query query = entityManager.createQuery(QL);
            EntityManagerFactoryUtils.applyTransactionTimeout(query, getEntityManagerFactory());
            return (T) query.getSingleResult();
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        }
    }

    @Override
    public <T> T findUniqueByQL(Class<T> clazz, String QL) {
        return findUniqueByQL(QL);
    }

    @Override
    public <T> T findUniqueByQL(String QL, Object... parameters) {
        try {
            Query query = entityManager.createQuery(QL);
            EntityManagerFactoryUtils.applyTransactionTimeout(query, getEntityManagerFactory());
            if (parameters != null && parameters.length > 0) {
                for (int i = 0; i < parameters.length; i++) {
                    query.setParameter(i + 1, parameters[i]);
                }
            }
            return (T) query.getSingleResult();
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        }
    }

    @Override
    public <T> T findUniqueByQL(Class<T> clazz, String QL, Object... parameters) {
        return findUniqueByQL(QL, parameters);
    }

    @Override
    public <T> T findUniqueByQL(String QL, Map<String, ?> parameters) {
        try {
            Query query = entityManager.createQuery(QL);
            EntityManagerFactoryUtils.applyTransactionTimeout(query, getEntityManagerFactory());
            if (parameters != null && !parameters.isEmpty()) {
                for (Map.Entry<String, ?> entry : parameters.entrySet()) {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }
            return (T) query.getSingleResult();
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        }
    }

    @Override
    public <T> T findUniqueByQL(Class<T> clazz, String QL, Map<String, ?> parameters) {
        return findUniqueByQL(QL, parameters);
    }

    @Override
    public <T> List<T> findListByQL(String QL) {
        try {
            Query query = entityManager.createQuery(QL);
            EntityManagerFactoryUtils.applyTransactionTimeout(query, getEntityManagerFactory());
            return query.getResultList();
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        }
    }

    @Override
    public <T> List<T> findListByQL(Class<T> clazz, String QL) {
        return findListByQL(QL);
    }

    @Override
    public <T> List<T> findListByQL(String QL, Object... parameters) {
        try {
            Query query = entityManager.createQuery(QL);
            EntityManagerFactoryUtils.applyTransactionTimeout(query, getEntityManagerFactory());
            if (parameters != null && parameters.length > 0) {
                for (int i = 0; i < parameters.length; i++) {
                    query.setParameter(i + 1, parameters[i]);
                }
            }
            return query.getResultList();
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        }
    }

    @Override
    public <T> List<T> findListByQL(Class<T> clazz, String QL, Object... parameters) {
        return findListByQL(QL, parameters);
    }

    @Override
    public <T> List<T> findListByQL(String QL, Map<String, ?> parameters) {
        try {
            Query query = entityManager.createQuery(QL);
            EntityManagerFactoryUtils.applyTransactionTimeout(query, getEntityManagerFactory());
            if (parameters != null && !parameters.isEmpty()) {
                for (Map.Entry<String, ?> entry : parameters.entrySet()) {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }
            return query.getResultList();
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        }
    }

    @Override
    public <T> List<T> findListByQL(Class<T> clazz, String QL, Map<String, ?> parameters) {
        return findListByQL(QL, parameters);
    }

    @Override
    public List<Member> findByNamedQuery(String name) {
        return findList(entityManager.createNamedQuery(name, getClazz()));
    }

    @Override
    public List<Member> findByNamedQuery(String name, Object... parameters) {
        return findList(entityManager.createNamedQuery(name, getClazz()), parameters);
    }

    @Override
    public List<Member> findByNamedQuery(String name, Map<String, ?> parameters) {
        return findList(entityManager.createNamedQuery(name, getClazz()), parameters);
    }

    @Override
    public <T> List<T> findListByNamedQuery(String name) {
        try {
            Query query = entityManager.createNamedQuery(name);
            EntityManagerFactoryUtils.applyTransactionTimeout(query, getEntityManagerFactory());
            return query.getResultList();
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        }
    }

    @Override
    public <T> List<T> findListByNamedQuery(String name, Object... parameters) {
        try {
            Query query = entityManager.createNamedQuery(name);
            EntityManagerFactoryUtils.applyTransactionTimeout(query, getEntityManagerFactory());
            if (parameters != null && parameters.length > 0) {
                for (int i = 0; i < parameters.length; i++) {
                    query.setParameter(i + 1, parameters[i]);
                }
            }
            return query.getResultList();
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        }
    }

    @Override
    public <T> List<T> findListByNamedQuery(Class<T> clazz, String name) {
        return findListByNamedQuery(name);
    }

    @Override
    public <T> List<T> findListByNamedQuery(String name, Map<String, ?> parameters) {
        try {
            Query query = entityManager.createNamedQuery(name);
            EntityManagerFactoryUtils.applyTransactionTimeout(query, getEntityManagerFactory());
            if (parameters != null && !parameters.isEmpty()) {
                for (Map.Entry<String, ?> entry : parameters.entrySet()) {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }
            return query.getResultList();
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        }
    }

    @Override
    public <T> List<T> findListByNamedQuery(Class<T> clazz, String name, Object... parameters) {
        return findListByNamedQuery(name, parameters);
    }

    @Override
    public <T> List<T> findListByNamedQuery(Class<T> clazz, String name, Map<String, ?> parameters) {
        return findListByNamedQuery(name, parameters);
    }

    @Override
    public Member initLazyCollection(Member entity, final String collectionFieldName) {
        final AtomicBoolean found = new AtomicBoolean(false);
        final String methodName = collectionFieldName.matches("^[a-z][A-Z]") ? collectionFieldName : collectionFieldName.length() > 1
                ? collectionFieldName.substring(0, 1).toUpperCase() + collectionFieldName.substring(1) : collectionFieldName.toUpperCase();
        final Object obj = entity;
        final EntityManager fem = entityManager;
        try {
            ReflectionUtils.doWithMethods(getClazz(),
                    new ReflectionUtils.MethodCallback() {
                        @Override
                        public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
                            try {
                                Method setter = obj.getClass().getMethod("s" + method.getName().substring(1), method.getReturnType());
                                Object fieldObj = method.invoke(obj, new Object[]{});
                                if (fieldObj instanceof Collection) {
                                    PersistenceUnitUtil puu = fem.getEntityManagerFactory().getPersistenceUnitUtil();
                                    if (!puu.isLoaded(obj, collectionFieldName)) {
                                        Member reattach = (Member) fem.merge(obj);
//                                            Member reattach = (E) em.find(obj.getClass(), puu.getIdentifier(obj));
                                        fieldObj = method.invoke(reattach, new Object[]{});
                                        ((Collection) fieldObj).size();
                                        setter.invoke(obj, fieldObj);
                                    }
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
            return (Member) obj;
        } catch (RuntimeException e) {
            throw EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(e);
        } finally {
            EntityManagerFactoryUtils.closeEntityManager(fem);
        }
    }
}
