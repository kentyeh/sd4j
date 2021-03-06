package springdao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Do everything that {@link DaoRepository } do, it's main purpose is
 * transaction management.<br/>
 * Standard JPA does not support custom isolation levels, So we must declare
 * {@link Transactional @Transactional} here.<br/>
 * 封裝{@link DaoRepository}的功能，只是為了交易控制.
 *
 * @author Kent Yeh
 * @param <E>
 */
@Transactional(readOnly = true)
public class RepositoryManager<E> {

    private DaoRepository<E> dao;
    protected static Logger logger = LogManager.getLogger(RepositoryManager.class);

    public DaoRepository<E> getDao() {
        return dao;
    }

    public void setDao(DaoRepository<E> dao) {
        this.dao = dao;
    }

    public E instanate() throws InstantiationException, IllegalAccessException {
        return dao.instanate();
    }

    public E findByPrimaryKey(Serializable primaryKey) {
        return dao == null ? null : dao.findByPrimaryKey(primaryKey);
    }

    public E findByPrimaryKey(Serializable primaryKey, String lockMode) {
        return dao == null ? null : dao.findByPrimaryKey(primaryKey, lockMode);
    }

    public E findByPrimaryKey(Serializable primaryKey, Map<String, Object> properties) {
        return dao == null ? null : dao.findByPrimaryKey(primaryKey, properties);
    }

    /**
     * @param properties Not used.不使用
     */
    public E findByPrimaryKey(Serializable primaryKey, String lockMode, Map<String, Object> properties) {
        return dao == null ? null : dao.findByPrimaryKey(primaryKey, lockMode, properties);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public E save(E entity) {
        return dao == null ? null : dao.save(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Collection<E> save(Collection<E> entities) {
        return dao == null ? null : dao.save(entities);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public E persist(E entity) {
        return dao == null ? null : dao.persist(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public E update(E entity) {
        return dao == null ? null : dao.update(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Collection<E> update(Collection<E> entities) {
        return dao == null ? null : dao.update(entities);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public E merge(E entity) {
        return dao == null ? null : dao.merge(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Collection<E> merge(Collection<E> entity) {
        return dao == null ? null : dao.merge(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public E saveOrUpdate(E entity) {
        return dao == null ? null : dao.saveOrUpdate(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Collection<E> saveOrUpdate(Collection<E> entities) {
        return dao == null ? null : dao.saveOrUpdate(entities);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Serializable primaryKey) {
        dao.delete(primaryKey);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Serializable primaryKey, String lockMode) {
        dao.delete(primaryKey, lockMode);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Collection<? extends Serializable> primaryKeys) {
        dao.delete(primaryKeys);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public E remove(E entity) {
        return dao.remove(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public E remove(E entity, String lockMode) {
        return dao.remove(entity, lockMode);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Collection<E> remove(Collection<E> entities) {
        return dao.remove(entities);
    }

    public E refresh(E entity) {
        return dao == null ? null : dao.refresh(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public E refresh(E entity, String lockMode) {
        return dao == null ? null : dao.refresh(entity, lockMode);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int sqlUpdate(String sql) {
        return dao == null ? null : dao.sqlUpdate(sql);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int sqlUpdate(String sql, Object... parameters) {
        return dao == null ? null : dao.sqlUpdate(sql, parameters);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int sqlUpdate(String sql, Map<String, ?>parameters) {
        return dao == null ? null : dao.sqlUpdate(sql, parameters);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<Integer> sqlUpdate(List<String> sqls) {
        return dao == null ? null : dao.sqlUpdate(sqls);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int bulkUpdate(String QL) {
        return dao == null ? null : dao.bulkUpdate(QL);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<Integer> bulkUpdate(List<String> QLs) {
        return dao == null ? null : dao.bulkUpdate(QLs);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int bulkUpdate(String QL, Object... parameters) {
        return dao == null ? null : dao.bulkUpdate(QL, parameters);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int bulkUpdate(String QL, Map<String, ?> parameters) {
        return dao == null ? null : dao.bulkUpdate(QL, parameters);
    }

    public String getEntityName() {
        return dao == null ? "" : dao.getEntityName();
    }

    public String $e() {
        return getEntityName();
    }

    public String getAliasName() {
        return dao == null ? "" : dao.getAliasName();
    }

    public String $a() {
        return getAliasName();
    }

    public String $a(String field) {
        return getAliasName() + "." + field;
    }

    public String $ea() {
        return getEntityName() + " AS " + getAliasName();
    }

    public List<E> findByCriteria(String qlCriteria) {
        return dao == null ? null : dao.findByCriteria(qlCriteria);
    }

    public List<E> findByCriteria(String qlCriteria, Object... parameters) {
        return dao == null ? null : dao.findByCriteria(qlCriteria, parameters);
    }

    public List<E> findByCriteria(String qlCriteria, Map<String, ?> parameters) {
        return dao == null ? null : dao.findByCriteria(qlCriteria, parameters);
    }

    public List<E> findByCriteria(String qlCriteria, int startPageNo, int pageSize, Object... parameters) {
        return dao == null ? null : dao.findByCriteria(qlCriteria, startPageNo, pageSize, parameters);
    }

    public List<E> findByCriteria(String qlCriteria, int startPageNo, int pageSize, Map<String, ?> parameters) {
        return dao == null ? null : dao.findByCriteria(qlCriteria, startPageNo, pageSize, parameters);
    }

    public List<E> findByCriteria(String qlCriteria, int startPageNo, int pageSize) {
        return dao == null ? null : dao.findByCriteria(qlCriteria, startPageNo, pageSize);
    }

    public E findFirstByCriteria(String qlCriteria) {
        List<E> result = findByCriteria(qlCriteria, 1, 1);
        return result.isEmpty() ? null : result.get(0);
    }

    public E findFirstByCriteria(String qlCriteria, Object... parameters) {
        List<E> result = findByCriteria(qlCriteria, 1, 1, parameters);
        return result.isEmpty() ? null : result.get(0);
    }

    public List<E> findByNamedQuery(String name) {
        return dao == null ? null : dao.findByNamedQuery(name);
    }

    public List<E> findByNamedQuery(String name, Object... parameters) {
        return dao == null ? null : dao.findByNamedQuery(name, parameters);
    }

    public List<E> findByNamedQuery(String name, Map<String, ?> parameters) {
        return dao == null ? null : dao.findByNamedQuery(name, parameters);
    }

    public List<E> findBySQLQuery(String sql) {
        return dao == null ? null : dao.findBySQLQuery(sql);
    }

    public List<E> findBySQLQuery(String sql, Object... parameters) {
        return dao == null ? null : dao.findBySQLQuery(sql, parameters);
    }

    public List<E> findBySQLQuery(String sql, Map<String, ?> parameters) {
        return dao == null ? null : dao.findBySQLQuery(sql, parameters);
    }

    public List<E> findBySQLQuery(String sql, String entityAlias) {
        return dao == null ? null : dao.findBySQLQuery(sql, entityAlias);
    }

    public List<E> findBySQLQuery(String sql, String entityAlias, Object... parameters) {
        return dao == null ? null : dao.findBySQLQuery(sql, entityAlias, parameters);
    }

    public <T> T findUniqueByQL(String QL) {
        if (dao == null) {
            return null;
        } else {
            return dao.findUniqueByQL(QL);
        }
    }

    public <T> T findUniqueByQL(Class<T> clazz, String QL) {
        return findUniqueByQL(QL);
    }

    public <T> T findUniqueByQL(String QL, Object... parameters) {
        if (dao == null) {
            return null;
        } else {
            return dao.findUniqueByQL(QL, parameters);
        }
    }

    public <T> T findUniqueByQL(Class<T> clazz, String QL, Object... parameters) {
        return findUniqueByQL(QL, parameters);
    }

    public <T> T findUniqueByQL(String QL, Map<String, ?> parameters) {
        if (dao == null) {
            return null;
        } else {
            return dao.findUniqueByQL(QL, parameters);
        }
    }

    public <T> T findUniqueByQL(Class<T> clazz, String QL, Map<String, ?> parameters) {
        return findUniqueByQL(QL, parameters);
    }

    public <T> List<T> findListByQL(String QL) {
        if (dao == null) {
            return null;
        } else {
            return dao.findListByQL(QL);
        }
    }

    public <T> List<T> findListByQL(Class<T> clazz, String QL) {
        return findListByQL(QL);
    }

    public <T> List<T> findListByQL(String QL, Object... parameters) {
        if (dao == null) {
            return null;
        } else {
            return dao.findListByQL(QL, parameters);
        }
    }

    public <T> List<T> findListByQL(Class<T> clazz, String QL, Object... parameters) {
        return findListByQL(QL, parameters);
    }

    public <T> List<T> findListByQL(String QL, Map<String, ?> parameters) {
        if (dao == null) {
            return null;
        } else {
            return dao.findListByQL(QL, parameters);
        }
    }

    public <T> List<T> findListByQL(Class<T> clazz, String QL, Map<String, ?> parameters) {
        return findListByQL(QL, parameters);
    }

    public <T> List<T> findListByNamedQuery(String name) {
        if (dao == null) {
            return null;
        } else {
            return dao.findListByNamedQuery(name);
        }
    }

    public <T> List<T> findListByNamedQuery(Class<T> clazz, String name) {
        return findListByNamedQuery(name);
    }

    public <T> List<T> findListByNamedQuery(String name, Object... parameters) {
        if (dao == null) {
            return null;
        } else {
            return dao.findListByNamedQuery(name, parameters);
        }

    }

    public <T> List<T> findListByNamedQuery(Class<T> clazz, String name, Object... parameters) {
        return findListByNamedQuery(name, parameters);
    }

    public <T> List<T> findListByNamedQuery(String name, Map<String, ?> parameters) {
        if (dao == null) {
            return null;
        } else {
            return dao.findListByNamedQuery(name, parameters);
        }

    }

    public <T> List<T> findListByNamedQuery(Class<T> clazz, String name, Map<String, ?> parameters) {
        return findListByNamedQuery(name, parameters);
    }

    public E initLazyCollection(E entity, String collectionFieldName) {
        return dao == null ? null : dao.initLazyCollection(entity, collectionFieldName);
    }

    public void clear() {
        if (dao != null) {
            dao.clear();
        }
    }
}
