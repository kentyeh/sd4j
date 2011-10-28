package springdao.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import springdao.DaoRepository;

/**
 *
 * @author Kent Yeh
 */
public abstract class AbstractSpringDao<E> extends JpaDaoSupport implements DaoRepository<E> {

    static Logger logger = LoggerFactory.getLogger(AbstractSpringDao.class);

    private LockModeType getLockMode(String lockMode) {
        LockModeType mode = LockModeType.NONE;
        try {
            mode = LockModeType.valueOf(lockMode);
        } catch (RuntimeException ex) {
            mode = LockModeType.NONE;
        }
        return mode;
    }

    @Override
    public E instanate() throws InstantiationException, IllegalAccessException {
        try {
            return getClazz().newInstance();
        } catch (InstantiationException ex) {
            logger.error("Instanate error", ex);
            throw ex;
        } catch (IllegalAccessException ex) {
            logger.error("Instanate error", ex);
            throw ex;
        }
    }

    @Override
    public void clear() {
        getJpaTemplate().execute(new JpaCallback<E>() {

            public E doInJpa(EntityManager em) throws PersistenceException {
                em.clear();
                return null;
            }
        });
    }

    @Override
    public boolean contains(final Object entity) {
        return getJpaTemplate().contains(entity);
    }

    @Override
    public E findByPrimaryKey(Serializable primaryKey) {
        return getJpaTemplate().find(getClazz(), primaryKey);
    }

    @Override
    public E findByPrimaryKey(final Serializable primaryKey, final String lockMode) {
        return getJpaTemplate().execute(new JpaCallback<E>() {

            public E doInJpa(EntityManager em) throws PersistenceException {
                return em.find(getClazz(), primaryKey, getLockMode(lockMode));
            }
        });
    }

    @Override
    public E findByPrimaryKey(final Serializable primaryKey, final Map<String, Object> properties) {
        return getJpaTemplate().execute(new JpaCallback<E>() {

            public E doInJpa(EntityManager em) throws PersistenceException {
                return em.find(getClazz(), primaryKey, properties);
            }
        });
    }

    @Override
    public E findByPrimaryKey(final Serializable primaryKey, final String lockMode, final Map<String, Object> properties) {
        return getJpaTemplate().execute(new JpaCallback<E>() {

            public E doInJpa(EntityManager em) throws PersistenceException {
                return em.find(getClazz(), primaryKey, getLockMode(lockMode), properties);
            }
        });
    }

    @Override
    public E save(E entity) {
        return contains(entity) ? merge(entity) : persist(entity);
    }

    @Override
    public Collection<E> save(final Collection<E> entities) {
        return getJpaTemplate().execute(new JpaCallback<Collection<E>>() {

            public Collection<E> doInJpa(EntityManager em) throws PersistenceException {
                for (E entity : entities) {
                    if (em.contains(entity)) {
                        em.merge(entity);
                    } else {
                        em.persist(entity);
                    }
                }
                em.flush();
                return entities;
            }
        });
    }

    @Override
    public E persist(E entity) {
        getJpaTemplate().persist(entity);
        getJpaTemplate().flush();
        return entity;
    }

    @Override
    public E persist(String entityName, E entity) {
        return persist(entity);
    }

    @Override
    public E update(E entity) {
        return merge(entity);
    }

    @Override
    public E update(final E entity, final String lockMode) {
        return getJpaTemplate().execute(new JpaCallback<E>() {

            public E doInJpa(EntityManager em) throws PersistenceException {
                E result = em.merge(entity);
                em.flush();
                em.lock(em, getLockMode(lockMode));
                return result;
            }
        });
    }

    @Override
    public Collection<E> update(Collection<E> entities) {
        return merge(entities);
    }

    @Override
    public E merge(E entity) {
        E result =  getJpaTemplate().merge(entity);
        getJpaTemplate().flush();
        return result;
    }

    @Override
    public Collection<E> merge(final Collection<E> entities) {
        return getJpaTemplate().execute(new JpaCallback<Collection<E>>() {

            public Collection<E> doInJpa(EntityManager em) throws PersistenceException {
                for (E entity : entities) {
                    em.merge(entity);
                }
                em.flush();
                return entities;
            }
        });
    }

    @Override
    public E merge(String entityName, E entity) {
        return merge(entity);
    }

    /**
     * Before using this method, look at 
     * <a href="http://blog.xebia.com/2009/03/23/jpa-implementation-patterns-saving-detached-entities/">saveOrUpdate vs. merge</a>.
     * @param entity
     * @return 
     */
    @Override
    public E saveOrUpdate(final E entity) {
        return merge(entity);
    }

    @Override
    public E saveOrUpdate(String entityName, E entity) {
        return merge(entity);
    }

    @Override
    public Collection<E> saveOrUpdate(Collection<E> entities) {
        return merge(entities);
    }

    @Override
    public void delete(final E entity) {
        getJpaTemplate().execute(new JpaCallback<Object>() {

            public Object doInJpa(EntityManager em) throws PersistenceException {
                em.remove(em.merge(entity));
                em.flush();
                return null;
            }
        });
    }

    @Override
    public void delete(final E entity, final String lockMode) {
        getJpaTemplate().execute(new JpaCallback<Object>() {

            public Object doInJpa(EntityManager em) throws PersistenceException {
                em.lock(em.merge(entity), getLockMode(lockMode));
                em.remove(entity);
                em.flush();
                return null;
            }
        });
    }

    @Override
    public void delete(final Collection<E> entities) {
        getJpaTemplate().execute(new JpaCallback<Object>() {

            public Object doInJpa(EntityManager em) throws PersistenceException {
                for (E entity : entities) {
                    em.remove(em.merge(entity));
                }
                em.flush();
                return null;
            }
        });
    }

    @Override
    public E lock(final E entity, final String lockMode) {
        return getJpaTemplate().execute(new JpaCallback<E>() {

            public E doInJpa(EntityManager em) throws PersistenceException {
                em.lock(entity, getLockMode(lockMode));
                return entity;
            }
        });
    }

    @Override
    public E lock(String entityName, E entity, String lockMode) {
        return lock(entity, lockMode);
    }

    @Override
    public E refresh(final E entity) {
        return getJpaTemplate().execute(new JpaCallback<E>() {

            public E doInJpa(EntityManager em) throws PersistenceException {
                em.refresh(entity);
                return entity;
            }
        });
    }

    @Override
    public E refresh(final E entity, final String lockMode) {
        return getJpaTemplate().execute(new JpaCallback<E>() {

            public E doInJpa(EntityManager em) throws PersistenceException {
                em.refresh(entity, getLockMode(lockMode));
                return entity;
            }
        });
    }

    @Override
    public int bulkUpdate(final String QL) {
        return getJpaTemplate().execute(new JpaCallback<Integer>() {

            public Integer doInJpa(EntityManager em) throws PersistenceException {
                return em.createQuery(QL).executeUpdate();
            }
        });
    }

    @Override
    public List<Integer> bulkUpdate(final List<String> QLs) {
        return getJpaTemplate().execute(new JpaCallback<List<Integer>>() {

            public List<Integer> doInJpa(EntityManager em) throws PersistenceException {
                List<Integer> result = new ArrayList<Integer>();
                for (String ql : QLs) {
                    result.add(em.createQuery(ql).executeUpdate());
                }
                return result;
            }
        });
    }

    @Override
    public int bulkUpdate(final String QL, final Object... parameters) {
        return getJpaTemplate().execute(new JpaCallback<Integer>() {

            public Integer doInJpa(EntityManager em) throws PersistenceException {
                Query query = em.createQuery(QL);
                int i = 0;
                for (Object paramter : parameters) {
                    query.setParameter(++i, paramter);
                }
                return query.executeUpdate();
            }
        });
    }

    @Override
    public int bulkUpdate(final String QL, final Map<String, ?> parameters) {
        return getJpaTemplate().execute(new JpaCallback<Integer>() {

            public Integer doInJpa(EntityManager em) throws PersistenceException {
                Query query = em.createQuery(QL);
                int i = 0;
                for (String key : parameters.keySet()) {
                    query.setParameter(key, parameters.get(key));
                }
                return query.executeUpdate();
            }
        });
    }

    @Override
    public String getEntityName() {
        return getClazz().getName();
    }

    @Override
    public String getAliasName() {
        return AliasHelper.getAlias(getClazz());
    }

    @Override
    public List<E> findByCriteria(String qlCriteria) {
        return getJpaTemplate().find(new StringBuilder("SELECT ").append(getAliasName()).append(" FROM ").
                append(getEntityName()).append("  ").append(getAliasName()).append(" ").append(qlCriteria).toString());
    }

    @Override
    public List<E> findByCriteria(String qlCriteria, Object... parameters) {
        return getJpaTemplate().find(new StringBuilder("SELECT ").append(getAliasName()).append(" FROM ").
                append(getEntityName()).append("  ").append(getAliasName()).append(" ").append(qlCriteria).toString(),
                parameters);
    }

    @Override
    public List<E> findByCriteria(String qlCriteria, Map<String, ?> parameters) {
        return getJpaTemplate().find(new StringBuilder("SELECT ").append(getAliasName()).append(" FROM ").
                append(getEntityName()).append("  ").append(getAliasName()).append(" ").append(qlCriteria).toString(),
                parameters);
    }

    @Override
    public List<E> findByJoinCriteria(String joins, String qlCriteria) {
        return getJpaTemplate().find(new StringBuilder("SELECT DISTINCT ").append(getAliasName()).
                append(" FROM ").append(getEntityName()).append(" ").append(getAliasName()).append(" JOIN ").
                append(joins).append(" ").append(qlCriteria).toString());
    }

    @Override
    public List<E> findByJoinCriteria(String joins, String qlCriteria, Object... parameters) {
        return getJpaTemplate().find(new StringBuilder("SELECT DISTINCT ").append(getAliasName()).
                append(" FROM ").append(getEntityName()).append(" ").append(getAliasName()).append(" ").
                append(joins).append(" ").append(qlCriteria).toString(), parameters);
    }

    @Override
    public List<E> findByJoinCriteria(String joins, String qlCriteria, Map<String, ?> parameters) {
        return getJpaTemplate().find(new StringBuilder("SELECT DISTINCT ").append(getAliasName()).
                append(" FROM ").append(getEntityName()).append(" ").append(getAliasName()).append(" ").
                append(joins).append(" ").append(qlCriteria).toString(), parameters);
    }

    @Override
    public List<E> findByCriteria(final String qlCriteria, final int startPageNo, final int pageSize, final Object... parameters) {
        if ((startPageNo < 1) || (pageSize < 1)) {
            return parameters != null || parameters.length == 0 ? findByCriteria(qlCriteria) : findByCriteria(qlCriteria, parameters);
        } else if (parameters == null || parameters.length == 0) {
            return findByCriteria(qlCriteria, startPageNo, pageSize);
        } else {
            return getJpaTemplate().execute(new JpaCallback<List<E>>() {

                public List<E> doInJpa(EntityManager em) throws PersistenceException {
                    StringBuilder sb = new StringBuilder("SELECT ").append(getAliasName()).append(" FROM ").
                            append(getEntityName()).append("  ").append(getAliasName()).append(" ").append(qlCriteria);
                    Query query = em.createQuery(sb.toString()).setFirstResult((startPageNo - 1) * pageSize).setMaxResults(pageSize);
                    getJpaTemplate().prepareQuery(query);
                    if (parameters != null && parameters.length > 0) {
                        int i = 0;
                        int maxi = query.getParameters().size();
                        for (Object param : parameters) {
                            if (i < maxi) {
                                query.setParameter(++i, param);
                            }
                        }
                    }
                    return query.getResultList();
                }
            });
        }
    }

    @Override
    public List<E> findByCriteria(final String qlCriteria, final int startPageNo, final int pageSize, final Map<String, ?> parameters) {
        if ((startPageNo < 1) || (pageSize < 1)) {
            return parameters == null || parameters.isEmpty() ? findByCriteria(qlCriteria) : findByCriteria(qlCriteria, parameters);
        } else if (parameters == null || parameters.isEmpty()) {
            return findByCriteria(qlCriteria, startPageNo, pageSize);
        } else {
            return getJpaTemplate().execute(new JpaCallback<List<E>>() {

                public List<E> doInJpa(EntityManager em) throws PersistenceException {
                    StringBuilder sb = new StringBuilder("SELECT ").append(getAliasName()).append(" FROM ").
                            append(getEntityName()).append("  ").append(getAliasName()).append(" ").append(qlCriteria);
                    Query query = em.createQuery(sb.toString()).setFirstResult((startPageNo - 1) * pageSize).setMaxResults(pageSize);
                    getJpaTemplate().prepareQuery(query);
                    if (parameters != null && !parameters.isEmpty()) {
                        for (String key : parameters.keySet()) {
                            query.setParameter(key, parameters.get(key));
                        }
                    }
                    return query.getResultList();
                }
            });
        }
    }

    @Override
    public List<E> findByJoinCriteria(final String joins, final String qlCriteria, final int startPageNo, final int pageSize,
            final Object... parameters) {
        if ((startPageNo < 1) || (pageSize < 1)) {
            return parameters == null || parameters.length == 0 ? findByJoinCriteria(joins, qlCriteria)
                    : findByJoinCriteria(joins, qlCriteria, parameters);
        } else if (parameters == null || parameters.length == 0) {
            return findByCriteria(qlCriteria, startPageNo, pageSize);
        } else {
            return getJpaTemplate().execute(new JpaCallback<List<E>>() {

                public List<E> doInJpa(EntityManager em) throws PersistenceException {
                    StringBuilder sb = new StringBuilder("SELECT DISTINCT ").append(getAliasName()).
                            append(" FROM ").append(getEntityName()).append(" ").append(getAliasName()).append(" ").
                            append(joins).append(" ").append(qlCriteria);
                    Query query = em.createQuery(sb.toString()).setFirstResult((startPageNo - 1) * pageSize).setMaxResults(pageSize);
                    getJpaTemplate().prepareQuery(query);
                    int i = 0;
                    int maxi = query.getParameters().size();
                    for (Object param : parameters) {
                        if (i < maxi) {
                            query.setParameter(++i, param);
                        }
                    }
                    return query.getResultList();
                }
            });
        }
    }

    @Override
    public List<E> findByJoinCriteria(final String joins, final String qlCriteria, final int startPageNo, final int pageSize,
            final Map<String, ?> parameters) {
        if ((startPageNo < 1) || (pageSize < 1)) {
            return parameters == null || parameters.isEmpty() ? findByJoinCriteria(joins, qlCriteria)
                    : findByJoinCriteria(joins, qlCriteria, parameters);
        } else if (parameters == null || parameters.isEmpty()) {
            return findByCriteria(qlCriteria, startPageNo, pageSize);
        } else {
            return getJpaTemplate().execute(new JpaCallback<List<E>>() {

                public List<E> doInJpa(EntityManager em) throws PersistenceException {
                    StringBuilder sb = new StringBuilder("SELECT DISTINCT ").append(getAliasName()).
                            append(" FROM ").append(getEntityName()).append(" ").append(getAliasName()).append(" ").
                            append(joins).append(" ").append(qlCriteria);
                    Query query = em.createQuery(sb.toString()).setFirstResult((startPageNo - 1) * pageSize).setMaxResults(pageSize);
                    getJpaTemplate().prepareQuery(query);
                    for (String key : parameters.keySet()) {
                        query.setParameter(key, parameters.get(key));
                    }
                    return query.getResultList();
                }
            });
        }
    }

    @Override
    public List<E> findByCriteria(final String qlCriteria, final int startPageNo, final int pageSize) {
        if ((startPageNo < 1) || (pageSize < 1)) {
            return findByCriteria(qlCriteria);
        } else {

            return getJpaTemplate().execute(new JpaCallback<List<E>>() {

                public List<E> doInJpa(EntityManager em) throws PersistenceException {
                    StringBuilder sb = new StringBuilder("SELECT ").append(getAliasName()).append(" FROM ").
                            append(getEntityName()).append("  ").append(getAliasName()).append(" ").append(qlCriteria);
                    Query query = em.createQuery(sb.toString()).setFirstResult((startPageNo - 1) * pageSize).setMaxResults(pageSize);
                    getJpaTemplate().prepareQuery(query);
                    return query.getResultList();
                }
            });
        }
    }

    @Override
    public List<E> findByJoinCriteria(String joins, final String qlCriteria, final int startPageNo, final int pageSize) {
        final StringBuilder sb = new StringBuilder("SELECT DISTINCT ").append(getAliasName()).
                append(" FROM ").append(getEntityName()).append(" ").append(getAliasName()).append(" ").
                append(joins).append(" ").append(qlCriteria);
        if ((startPageNo < 1) || (pageSize < 1)) {
            return getJpaTemplate().find(sb.toString());
        } else {
            return getJpaTemplate().execute(new JpaCallback<List<E>>() {

                public List<E> doInJpa(EntityManager em) throws PersistenceException {
                    Query query = em.createQuery(sb.toString()).setFirstResult((startPageNo - 1) * pageSize).setMaxResults(pageSize);
                    getJpaTemplate().prepareQuery(query);
                    return query.getResultList();
                }
            });
        }
    }

    @Override
    public List<E> findBySQLQuery(final String sql) {
        return getJpaTemplate().execute(new JpaCallback<List<E>>() {

            public List<E> doInJpa(EntityManager em) throws PersistenceException {
                Query query = em.createNativeQuery(sql, getClazz());
                getJpaTemplate().prepareQuery(query);
                return query.getResultList();
            }
        });
    }

    @Override
    public List<E> findBySQLQuery(final String sql, final Object... parameters) {
        return getJpaTemplate().execute(new JpaCallback<List<E>>() {

            public List<E> doInJpa(EntityManager em) throws PersistenceException {
                Query query = em.createNativeQuery(sql, getClazz());
                getJpaTemplate().prepareQuery(query);
                if (parameters != null && parameters.length > 0) {
                    int i = 0;
                    int maxi = query.getParameters().size();
                    for (Object param : parameters) {
                        if (i < maxi) {
                            query.setParameter(++i, param);
                        }
                    }
                }
                return query.getResultList();
            }
        });
    }

    @Override
    public List<E> findBySQLQuery(final String sql, final Map<String, ?> parameters) {
        return getJpaTemplate().execute(new JpaCallback<List<E>>() {

            public List<E> doInJpa(EntityManager em) throws PersistenceException {
                Query query = em.createNativeQuery(sql, getClazz());
                getJpaTemplate().prepareQuery(query);
                if (parameters != null && !parameters.isEmpty()) {
                    for (String key : parameters.keySet()) {
                        query.setParameter(key, parameters.get(key));
                    }
                }
                return query.getResultList();
            }
        });
    }

    /**
     * 
     * @param sql
     * @param entityAlias not work here
     * @return 
     */
    @Override
    public List<E> findBySQLQuery(String sql, String entityAlias) {
        return findBySQLQuery(sql);
    }

    /**
     * 
     * @param sql
     * @param entityAlias not work here.
     * @param parameters
     * @return 
     */
    @Override
    public List<E> findBySQLQuery(String sql, String entityAlias, Object... parameters) {
        return findBySQLQuery(sql, parameters);
    }

    @Override
    public List<E> findBySQLQuery(String sql, String entityAlias, Map<String, ?> parameters) {
        return findBySQLQuery(sql, parameters);
    }

    @Override
    public Object findUniqueByQL(final String QL) {
        return getJpaTemplate().execute(new JpaCallback<Object>() {

            public Object doInJpa(EntityManager em) throws PersistenceException {
                Query query = em.createQuery(QL);
                getJpaTemplate().prepareQuery(query);
                return query.getSingleResult();
            }
        });
    }

    @Override
    public Object findUnique(final String QL, final Object... parameters) {
        return getJpaTemplate().execute(new JpaCallback<Object>() {

            public Object doInJpa(EntityManager em) throws PersistenceException {
                Query query = em.createQuery(QL);
                getJpaTemplate().prepareQuery(query);
                if (parameters != null && parameters.length > 0) {
                    int i = 0;
                    int maxi = query.getParameters().size();
                    for (Object param : parameters) {
                        if (i < maxi) {
                            query.setParameter(++i, param);
                        }
                    }
                }
                return query.getSingleResult();
            }
        });
    }

    @Override
    public Object findUnique(final String QL, final Map<String, ?> parameters) {
        return getJpaTemplate().execute(new JpaCallback<Object>() {

            public Object doInJpa(EntityManager em) throws PersistenceException {
                Query query = em.createQuery(QL);
                getJpaTemplate().prepareQuery(query);
                if (parameters != null && !parameters.isEmpty()) {
                    for (String key : parameters.keySet()) {
                        query.setParameter(key, parameters.get(key));
                    }
                }
                return query.getSingleResult();
            }
        });
    }

    @Override
    public List<Object> findListByQL(final String QL) {
        return getJpaTemplate().execute(new JpaCallback<List<Object>>() {

            public List<Object> doInJpa(EntityManager em) throws PersistenceException {
                Query query = em.createNativeQuery(QL);
                getJpaTemplate().prepareQuery(query);
                return query.getResultList();
            }
        });
    }

    @Override
    public List<Object> findListByQL(final String QL, final Object... parameters) {
        return getJpaTemplate().execute(new JpaCallback<List<Object>>() {

            public List<Object> doInJpa(EntityManager em) throws PersistenceException {
                Query query = em.createNativeQuery(QL);
                getJpaTemplate().prepareQuery(query);
                if (parameters != null && parameters.length > 0) {
                    int i = 0;
                    int maxi = query.getParameters().size();
                    for (Object param : parameters) {
                        if (i < maxi) {
                            query.setParameter(++i, param);
                        }
                    }
                }
                return query.getResultList();
            }
        });
    }

    @Override
    public List<Object> findListByQL(final String QL, final Map<String, ?> parameters) {
        return getJpaTemplate().execute(new JpaCallback<List<Object>>() {

            public List<Object> doInJpa(EntityManager em) throws PersistenceException {
                Query query = em.createNativeQuery(QL);
                getJpaTemplate().prepareQuery(query);
                if (parameters != null && !parameters.isEmpty()) {
                    for (String key : parameters.keySet()) {
                        query.setParameter(key, parameters.get(key));
                    }
                }
                return query.getResultList();
            }
        });
    }

    public List<E> findByNamedQuery(final String name) {
        return getJpaTemplate().executeFind(new JpaCallback<List<E>>() {

            public List<E> doInJpa(EntityManager em) throws PersistenceException {
                return em.createNamedQuery(name, getClazz()).getResultList();
            }
        });
    }

    public List<E> findByNamedQuery(final String name, final Object... parameters) {
        return getJpaTemplate().executeFind(new JpaCallback<List<E>>() {

            public List<E> doInJpa(EntityManager em) throws PersistenceException {
                Query query = em.createNamedQuery(name, getClazz());
                getJpaTemplate().prepareQuery(query);
                if (parameters != null && parameters.length > 0) {
                    int i = 0;
                    int maxi = query.getParameters().size();
                    for (Object param : parameters) {
                        if (i < maxi) {
                            query.setParameter(++i, param);
                        }
                    }
                }
                return query.getResultList();
            }
        });
    }

    public List<E> findByNamedQuery(final String name, final Map<String, ?> parameters) {
        return getJpaTemplate().executeFind(new JpaCallback<List<E>>() {

            public List<E> doInJpa(EntityManager em) throws PersistenceException {
                Query query = em.createNamedQuery(name, getClazz());
                getJpaTemplate().prepareQuery(query);
                if (parameters != null && !parameters.isEmpty()) {
                    for (String key : parameters.keySet()) {
                        query.setParameter(key, parameters.get(key));
                    }
                }
                return query.getResultList();
            }
        });
    }

    public List<Object> findListByNamedQuery(final String name) {
        return getJpaTemplate().executeFind(new JpaCallback<List<Object>>() {

            public List<Object> doInJpa(EntityManager em) throws PersistenceException {
                return em.createNamedQuery(name).getResultList();
            }
        });
    }

    public List<Object> findListByNamedQuery(final String name, final Object... parameters) {
        return getJpaTemplate().executeFind(new JpaCallback<List<Object>>() {

            public List<Object> doInJpa(EntityManager em) throws PersistenceException {
                Query query = em.createNamedQuery(name);
                getJpaTemplate().prepareQuery(query);
                if (parameters != null && parameters.length > 0) {
                    int i = 0;
                    int maxi = query.getParameters().size();
                    for (Object param : parameters) {
                        if (i < maxi) {
                            query.setParameter(++i, param);
                        }
                    }
                }
                return query.getResultList();
            }
        });
    }

    public List<Object> findListByNamedQuery(final String name, final Map<String, ?> parameters) {
        return getJpaTemplate().executeFind(new JpaCallback<List<Object>>() {

            public List<Object> doInJpa(EntityManager em) throws PersistenceException {
                Query query = em.createNamedQuery(name);
                getJpaTemplate().prepareQuery(query);
                if (parameters != null && !parameters.isEmpty()) {
                    for (String key : parameters.keySet()) {
                        query.setParameter(key, parameters.get(key));
                    }
                }
                return query.getResultList();
            }
        });
    }
}
