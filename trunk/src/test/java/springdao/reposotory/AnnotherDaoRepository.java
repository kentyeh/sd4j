package springdao.reposotory;

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
import springdao.support.AliasHelper;
import springdao.DaoRepository;
import springdao.model.Member;

/**
 *
 * @author Kent Yeh
 */
public class AnnotherDaoRepository extends JpaDaoSupport implements DaoRepository<Member> {

    static Logger logger = LoggerFactory.getLogger(AnnotherDaoRepository.class);

    public Class<Member> getClazz() {
        return Member.class;
    }

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
    public Member instanate() throws InstantiationException, IllegalAccessException {
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
        getJpaTemplate().execute(new JpaCallback<Member>() {

            public Member doInJpa(EntityManager em) throws PersistenceException {
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
    public Member findByPrimaryKey(Serializable primaryKey) {
        return getJpaTemplate().find(getClazz(), primaryKey);
    }

    @Override
    public Member findByPrimaryKey(final Serializable primaryKey, final String lockMode) {
        return getJpaTemplate().execute(new JpaCallback<Member>() {

            public Member doInJpa(EntityManager em) throws PersistenceException {
                return em.find(getClazz(), primaryKey, getLockMode(lockMode));
            }
        });
    }

    @Override
    public Member findByPrimaryKey(final Serializable primaryKey, final Map<String, Object> properties) {
        return getJpaTemplate().execute(new JpaCallback<Member>() {

            public Member doInJpa(EntityManager em) throws PersistenceException {
                return em.find(getClazz(), primaryKey, properties);
            }
        });
    }

    @Override
    public Member findByPrimaryKey(final Serializable primaryKey, final String lockMode, final Map<String, Object> properties) {
        return getJpaTemplate().execute(new JpaCallback<Member>() {

            public Member doInJpa(EntityManager em) throws PersistenceException {
                return em.find(getClazz(), primaryKey, getLockMode(lockMode), properties);
            }
        });
    }

    @Override
    public Member save(Member entity) {
        if (contains(entity)) {
            return merge(entity);
        } else {
            return persist(entity);
        }
    }

    @Override
    public Collection<Member> save(final Collection<Member> entities) {
        getJpaTemplate().execute(new JpaCallback<Object>() {

            public Object doInJpa(EntityManager em) throws PersistenceException {
                int i = 0;
                for (Member entity : entities) {
                    if (em.contains(entity)) {
                        em.merge(entity);
                    } else {
                        em.persist(entity);
                    }
                    if (++i % 20 == 0) {
                        em.flush();
                        em.clear();
                    }
                }
                if (++i % 20 > 0) {
                    em.flush();
                    em.clear();
                }
                return null;
            }
        });
        return entities;
    }

    @Override
    public Member persist(Member entity) {
        getJpaTemplate().persist(entity);
        return entity;
    }

    @Override
    public Member persist(String entityName, Member entity) {
        return persist(entity);
    }

    @Override
    public Member update(Member entity) {
        return merge(entity);
    }

    @Override
    public Member update(final Member entity, final String lockMode) {
        return getJpaTemplate().execute(new JpaCallback<Member>() {

            public Member doInJpa(EntityManager em) throws PersistenceException {
                Member result = em.merge(entity);
                em.lock(em, getLockMode(lockMode));
                return result;
            }
        });
    }

    @Override
    public Collection<Member> update(Collection<Member> entities) {
        return merge(entities);
    }

    @Override
    public Member merge(Member entity) {
        return getJpaTemplate().merge(entity);
    }

    @Override
    public Collection<Member> merge(final Collection<Member> entities) {
        getJpaTemplate().execute(new JpaCallback<Object>() {

            public Object doInJpa(EntityManager em) throws PersistenceException {
                int i = 0;
                for (Member entity : entities) {
                    em.merge(entity);
                    if (++i % 20 == 0) {
                        em.flush();
                        em.clear();
                    }
                }
                if (++i % 20 > 0) {
                    em.flush();
                    em.clear();
                }
                return null;
            }
        });
        return entities;

    }

    @Override
    public Member merge(String entityName, Member entity) {
        return merge(entity);
    }

    /**
     * Before using this method, look at 
     * <a href="http://blog.xebia.com/2009/03/23/jpa-implementation-patterns-saving-detached-entities/">saveOrUpdate vs. merge</a>.
     * @param entity
     * @return 
     */
    @Override
    public Member saveOrUpdate(final Member entity) {
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
    public void delete(final Member entity) {
        getJpaTemplate().execute(new JpaCallback<Object>() {

            public Object doInJpa(EntityManager em) throws PersistenceException {
                em.remove(em.merge(entity));
                return null;
            }
        });
    }

    @Override
    public void delete(final Member entity, final String lockMode) {
        getJpaTemplate().execute(new JpaCallback<Object>() {

            public Object doInJpa(EntityManager em) throws PersistenceException {
                em.lock(em.merge(entity), getLockMode(lockMode));
                em.remove(entity);
                return null;
            }
        });
    }

    @Override
    public void delete(final Collection<Member> entities) {
        getJpaTemplate().execute(new JpaCallback<Object>() {

            public Object doInJpa(EntityManager em) throws PersistenceException {
                for (Member entity : entities) {
                    em.remove(em.merge(entity));
                }
                return null;
            }
        });
    }

    @Override
    public Member lock(final Member entity, final String lockMode) {
        return getJpaTemplate().execute(new JpaCallback<Member>() {

            public Member doInJpa(EntityManager em) throws PersistenceException {
                em.lock(entity, getLockMode(lockMode));
                return entity;
            }
        });
    }

    @Override
    public Member lock(String entityName, Member entity, String lockMode) {
        return lock(entity, lockMode);
    }

    @Override
    public Member refresh(final Member entity) {
        return getJpaTemplate().execute(new JpaCallback<Member>() {

            public Member doInJpa(EntityManager em) throws PersistenceException {
                em.refresh(entity);
                return entity;
            }
        });
    }

    @Override
    public Member refresh(final Member entity, final String lockMode) {
        return getJpaTemplate().execute(new JpaCallback<Member>() {

            public Member doInJpa(EntityManager em) throws PersistenceException {
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
    public List<Member> findByCriteria(String qlCriteria) {
        return getJpaTemplate().find(new StringBuilder("SELECT ").append(getAliasName()).append(" FROM ").
                append(getEntityName()).append("  ").append(getAliasName()).append(" ").append(qlCriteria).toString());
    }

    @Override
    public List<Member> findByCriteria(String qlCriteria, Object... parameters) {
        return getJpaTemplate().find(new StringBuilder("SELECT ").append(getAliasName()).append(" FROM ").
                append(getEntityName()).append("  ").append(getAliasName()).append(" ").append(qlCriteria).toString(),
                parameters);
    }

    @Override
    public List<Member> findByCriteria(String qlCriteria, Map<String, ?> parameters) {
        return getJpaTemplate().find(new StringBuilder("SELECT ").append(getAliasName()).append(" FROM ").
                append(getEntityName()).append("  ").append(getAliasName()).append(" ").append(qlCriteria).toString(),
                parameters);
    }

    @Override
    public List<Member> findByJoinCriteria(String joins, String qlCriteria) {
        return getJpaTemplate().find(new StringBuilder("SELECT DISTINCT ").append(getAliasName()).
                append(" FROM ").append(getEntityName()).append(" ").append(getAliasName()).append(" JOIN ").
                append(joins).append(" ").append(qlCriteria).toString());
    }

    @Override
    public List<Member> findByJoinCriteria(String joins, String qlCriteria, Object... parameters) {
        return getJpaTemplate().find(new StringBuilder("SELECT DISTINCT ").append(getAliasName()).
                append(" FROM ").append(getEntityName()).append(" ").append(getAliasName()).append(" ").
                append(joins).append(" ").append(qlCriteria).toString(), parameters);
    }

    @Override
    public List<Member> findByJoinCriteria(String joins, String qlCriteria, Map<String, ?> parameters) {
        return getJpaTemplate().find(new StringBuilder("SELECT DISTINCT ").append(getAliasName()).
                append(" FROM ").append(getEntityName()).append(" ").append(getAliasName()).append(" ").
                append(joins).append(" ").append(qlCriteria).toString(), parameters);
    }

    @Override
    public List<Member> findByCriteria(final String qlCriteria, final int startPageNo, final int pageSize, final Object... parameters) {
        if ((startPageNo < 1) || (pageSize < 1)) {
            return parameters != null || parameters.length == 0 ? findByCriteria(qlCriteria) : findByCriteria(qlCriteria, parameters);
        } else if (parameters == null || parameters.length == 0) {
            return findByCriteria(qlCriteria, startPageNo, pageSize);
        } else {
            return getJpaTemplate().execute(new JpaCallback<List<Member>>() {

                public List<Member> doInJpa(EntityManager em) throws PersistenceException {
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
    public List<Member> findByCriteria(final String qlCriteria, final int startPageNo, final int pageSize, final Map<String, ?> parameters) {
        if ((startPageNo < 1) || (pageSize < 1)) {
            return parameters == null || parameters.isEmpty() ? findByCriteria(qlCriteria) : findByCriteria(qlCriteria, parameters);
        } else if (parameters == null || parameters.isEmpty()) {
            return findByCriteria(qlCriteria, startPageNo, pageSize);
        } else {
            return getJpaTemplate().execute(new JpaCallback<List<Member>>() {

                public List<Member> doInJpa(EntityManager em) throws PersistenceException {
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
    public List<Member> findByJoinCriteria(final String joins, final String qlCriteria, final int startPageNo, final int pageSize,
            final Object... parameters) {
        if ((startPageNo < 1) || (pageSize < 1)) {
            return parameters == null || parameters.length == 0 ? findByJoinCriteria(joins, qlCriteria)
                    : findByJoinCriteria(joins, qlCriteria, parameters);
        } else if (parameters == null || parameters.length == 0) {
            return findByCriteria(qlCriteria, startPageNo, pageSize);
        } else {
            return getJpaTemplate().execute(new JpaCallback<List<Member>>() {

                public List<Member> doInJpa(EntityManager em) throws PersistenceException {
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
    public List<Member> findByJoinCriteria(final String joins, final String qlCriteria, final int startPageNo, final int pageSize,
            final Map<String, ?> parameters) {
        if ((startPageNo < 1) || (pageSize < 1)) {
            return parameters == null || parameters.isEmpty() ? findByJoinCriteria(joins, qlCriteria)
                    : findByJoinCriteria(joins, qlCriteria, parameters);
        } else if (parameters == null || parameters.isEmpty()) {
            return findByCriteria(qlCriteria, startPageNo, pageSize);
        } else {
            return getJpaTemplate().execute(new JpaCallback<List<Member>>() {

                public List<Member> doInJpa(EntityManager em) throws PersistenceException {
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
    public List<Member> findByCriteria(final String qlCriteria, final int startPageNo, final int pageSize) {
        if ((startPageNo < 1) || (pageSize < 1)) {
            return findByCriteria(qlCriteria);
        } else {

            return getJpaTemplate().execute(new JpaCallback<List<Member>>() {

                public List<Member> doInJpa(EntityManager em) throws PersistenceException {
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
    public List<Member> findByJoinCriteria(String joins, final String qlCriteria, final int startPageNo, final int pageSize) {
        final StringBuilder sb = new StringBuilder("SELECT DISTINCT ").append(getAliasName()).
                append(" FROM ").append(getEntityName()).append(" ").append(getAliasName()).append(" ").
                append(joins).append(" ").append(qlCriteria);
        if ((startPageNo < 1) || (pageSize < 1)) {
            return getJpaTemplate().find(sb.toString());
        } else {
            return getJpaTemplate().execute(new JpaCallback<List<Member>>() {

                public List<Member> doInJpa(EntityManager em) throws PersistenceException {
                    Query query = em.createQuery(sb.toString()).setFirstResult((startPageNo - 1) * pageSize).setMaxResults(pageSize);
                    getJpaTemplate().prepareQuery(query);
                    return query.getResultList();
                }
            });
        }
    }

    @Override
    public List<Member> findBySQLQuery(final String sql) {
        return getJpaTemplate().execute(new JpaCallback<List<Member>>() {

            public List<Member> doInJpa(EntityManager em) throws PersistenceException {
                Query query = em.createNativeQuery(sql, getClazz());
                getJpaTemplate().prepareQuery(query);
                return query.getResultList();
            }
        });
    }

    @Override
    public List<Member> findBySQLQuery(final String sql, final Object... parameters) {
        return getJpaTemplate().execute(new JpaCallback<List<Member>>() {

            public List<Member> doInJpa(EntityManager em) throws PersistenceException {
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
    public List<Member> findBySQLQuery(final String sql, final Map<String, ?> parameters) {
        return getJpaTemplate().execute(new JpaCallback<List<Member>>() {

            public List<Member> doInJpa(EntityManager em) throws PersistenceException {
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
    public List<Member> findBySQLQuery(String sql, String entityAlias) {
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
    public List<Member> findBySQLQuery(String sql, String entityAlias, Object... parameters) {
        return findBySQLQuery(sql, parameters);
    }

    @Override
    public List<Member> findBySQLQuery(String sql, String entityAlias, Map<String, ?> parameters) {
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

    public List<Member> findByNamedQuery(final String name) {
        return getJpaTemplate().executeFind(new JpaCallback<List<Member>>() {

            public List<Member> doInJpa(EntityManager em) throws PersistenceException {
                return em.createNamedQuery(name, getClazz()).getResultList();
            }
        });
    }

    public List<Member> findByNamedQuery(final String name, final Object... parameters) {
        return getJpaTemplate().executeFind(new JpaCallback<List<Member>>() {

            public List<Member> doInJpa(EntityManager em) throws PersistenceException {
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

    public List<Member> findByNamedQuery(final String name, final Map<String, ?> parameters) {
        return getJpaTemplate().executeFind(new JpaCallback<List<Member>>() {

            public List<Member> doInJpa(EntityManager em) throws PersistenceException {
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
