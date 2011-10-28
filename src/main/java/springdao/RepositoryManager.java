package springdao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Standard JPA does not support custom isolation levels, So we must declare {@link Transactional @Transactional} here.
 * @author Kent Yeh
 */
@Transactional(readOnly = true)
public class RepositoryManager<E> {

    DaoRepository<E> dao;
    protected static Logger logger = LoggerFactory.getLogger(RepositoryManager.class);

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
    public E persist(String entityName, E entity) {
        return dao == null ? null : dao.persist(entityName, entity);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public E update(E entity) {
        return dao == null ? null : dao.update(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public E update(E entity, String lockMode) {
        return dao == null ? null : dao.update(entity, lockMode);
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
    public E merge(String entityName, E entity) {
        return dao == null ? null : dao.merge(entityName, entity);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public E saveOrUpdate(E entity) {
        return dao == null ? null : dao.saveOrUpdate(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public E saveOrUpdate(String entityName, E entity) {
        return dao == null ? null : dao.saveOrUpdate(entityName, entity);
    }

    public Collection<E> saveOrUpdate(Collection<E> entities) {
        return dao == null ? null : dao.save(entities);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void remove(E entity) {
        dao.delete(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void remove(E entity, String lockMode) {
        dao.delete(entity, lockMode);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void remove(Collection<E> entities) {
        dao.delete(entities);
    }

    public E refresh(E entity) {
        return dao == null ? null : dao.refresh(entity);
    }

    public E refresh(E entity, String lockMode) {
        return dao == null ? null : dao.refresh(entity, lockMode);
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

    public String getEntityName() {
        return dao == null ? "" : dao.getEntityName();
    }

    public String getAliasName() {
        return dao == null ? "" : dao.getAliasName();
    }

    public List<E> findByCriteria(String qlCriteria) {
        return dao == null ? null : dao.findByCriteria(qlCriteria);
    }

    public List<E> findByCriteria(String qlCriteria, Object... parameters) {
        return dao == null ? null : dao.findByCriteria(qlCriteria, parameters);
    }

    public List<E> findByJoinCriteria(String joins, String qlCriteria) {
        return dao == null ? null : dao.findByJoinCriteria(joins, qlCriteria);
    }

    public List<E> findByJoinCriteria(String joins, String qlCriteria, Object... parameters) {
        return dao == null ? null : dao.findByJoinCriteria(joins, qlCriteria, parameters);
    }

    public List<E> findByCriteria(String qlCriteria, int startPageNo, int pageSize, Object... parameters) {
        return dao == null ? null : dao.findByCriteria(qlCriteria, startPageNo, pageSize, parameters);
    }

    public List<E> findByJoinCriteria(String joins, String qlCriteria, int startPageNo, int pageSize, Object... parameters) {
        return dao == null ? null : dao.findByJoinCriteria(joins, qlCriteria, startPageNo, pageSize, parameters);
    }

    public List<E> findByCriteria(String qlCriteria, int startPageNo, int pageSize) {
        return dao == null ? null : dao.findByCriteria(qlCriteria, startPageNo, pageSize);
    }

    public List<E> findByJoinCriteria(String joins, String qlCriteria, int startPageNo, int pageSize) {
        return dao == null ? null : dao.findByJoinCriteria(joins, qlCriteria, startPageNo, pageSize);
    }

    public E findFirstByCriteria(String qlCriteria) {
        List<E> result = findByCriteria(qlCriteria, 1, 1);
        return result.isEmpty() ? null : result.get(0);
    }

    public E findFirstByCriteria(String qlCriteria, Object... parameters) {
        List<E> result = findByCriteria(qlCriteria, 1, 1, parameters);
        return result.isEmpty() ? null : result.get(0);
    }

    public E findFirstByJoinCriteria(String joins, String qlCriteria) {
        List<E> result = findByJoinCriteria(joins, qlCriteria, 1, 1);
        return result.isEmpty() ? null : result.get(0);
    }

    public E findFirstByJoinCriteria(String joins, String qlCriteria, Object... parameters) {
        List<E> result = findByJoinCriteria(joins, qlCriteria, 1, 1, parameters);
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

    public List<E> findBySQLQuery(String sql, String entityAlias) {
        return dao == null ? null : dao.findBySQLQuery(sql, entityAlias);
    }

    public List<E> findBySQLQuery(String sql, String entityAlias, Object... parameters) {
        return dao == null ? null : dao.findBySQLQuery(sql, entityAlias, parameters);
    }

    public Object findUniqueByQL(String QL) {
        return dao == null ? null : dao.findUniqueByQL(QL);
    }

    public Object findUnique(String QL, Object... parameters) {
        return dao == null ? null : dao.findUnique(QL, parameters);
    }

    public List<Object> findListByQL(String QL) {
        return dao == null ? null : dao.findListByQL(QL);
    }

    public List<Object> findListByQL(String QL, Object... parameters) {
        return dao == null ? null : dao.findListByQL(QL, parameters);
    }

    public List<Object> findListByNamedQuery(String name) {
        return dao == null ? null : dao.findListByNamedQuery(name);
    }

    public List<Object> findListByNamedQuery(String name, Object... parameters) {
        return dao == null ? null : dao.findListByNamedQuery(name, parameters);
    }

    public List<Object> findListByNamedQuery(String name, Map<String, ?> parameters) {
        return dao == null ? null : dao.findListByNamedQuery(name, parameters);
    }
}
