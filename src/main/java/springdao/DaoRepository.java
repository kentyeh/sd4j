package springdao;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.ManyToOne;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Kent Yeh
 */
public interface DaoRepository<E> {

    /**
     * return type of handle object.<br/>
     * 取回DAO物件真正對應所要處理的物件
     *
     * @return target object class.
     */
    public Class<E> getClazz();

    /**
     * build a instance of &lt;E&gt;.
     *
     * @return instance of {@link #getClazz() getClazz()}
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     */
    public E instanate() throws InstantiationException, IllegalAccessException;

    /**
     * Completely clear the session. Evict all loaded instances and cancel all
     * pending saves, updates and deletions. Do not close open iterators or
     * instances of ScrollableResults.
     */
    public void clear();

    /**
     * Check if this instance is associated with this Session.
     *
     * @param entity
     * @return True/False
     */
    boolean contains(Object entity);

    /**
     * Find entity with serializable primaryKey.<br/>
     * 以主鍵找尋物件
     *
     * @param primaryKey 主鍵
     * @return entity/物件
     */
    public E findByPrimaryKey(Serializable primaryKey);

    /**
     * Find entity with serializable id and lockMode.<br/>
     *
     * @param primaryKey
     * @param lockMode
     * @return entity/物件
     */
    public E findByPrimaryKey(Serializable primaryKey, String lockMode);

    public E findByPrimaryKey(Serializable primaryKey, Map<String, Object> properties);

    public E findByPrimaryKey(Serializable primaryKey, String lockMode, Map<String, Object> properties);

    /**
     * Save entiry 儲存物件
     *
     * @param entity entity(物件)
     * @return saved entity/儲存物件
     */
    public E save(E entity);

    /**
     * Save multipal entities.<br/>
     * 一次儲存多個物件
     *
     * @param entities
     * @return saved entities/儲存物件
     */
    public Collection<E> save(Collection<E> entities);

    /*
     * Make an instance managed and persistent. 
     */
    public E persist(E entity);

    /**
     * update entity<br/>
     * 更新物件
     *
     * @param entity 物件
     * @return merged entity/更新後的物件
     */
    public E update(E entity);

    /**
     * update multipal entities<br/>
     * 一次更新多個物件
     *
     * @param entities
     * @return merged entities/更新後的物件
     */
    public Collection<E> update(Collection<E> entities);

    public E merge(E entity);

    public Collection<E> merge(Collection<E> entities);

    /**
     * Save or update entity<br/>
     * 儲存或更新物件
     *
     * @param entity 物件
     * @return merged entity/更新後的物件
     */
    public E saveOrUpdate(E entity);

    /**
     * Save or update multipal entities<br/>
     * 一次儲存或更新多個物件
     *
     * @param entities
     * @return merged entities/更新後的物件
     */
    public Collection<E> saveOrUpdate(Collection<E> entities);

    /**
     * Delete entity by primary key.<br/>
     * 藉由主鍵刪除物件<br/>
     * <div style="color:red;font-weight:bold"> Notice:</div>
     * A child entity with a {@link ManyToOne &#064;ManyToOne} reference to a
     * parent entity can't be delete directed by {@link Dao &#064;Dao} or
     * {@link DaoManager &#064;DaoManager}, It should be delete like<br/>
     * <div style="color:red;font-weight:bold"> 注意:</div>
     * 當子物件包含{@link ManyToOne &#064;ManyToOne}參考到父物件時，無法直接由
     * {@link Dao &#064;Dao} or
     * {@link DaoManager &#064;DaoManager}刪除，必須以下方代碼進行子物件刪除
     * <pre>
     * &nbsp;&nbsp;Parent parent = parentDao.merge(child.getParent());
     * &nbsp;&nbsp;child.setParent(null);
     * &nbsp;&nbsp;parent.getChildren().remove(child);
     * &nbsp;&nbsp;parentDao.merge(parent);
     * </pre> Or it can be deleted by issue a blukUpdate command instead like
     * <br/>
     * 或是使用以下代碼刪除<br/>
     * <div style="color:blue"><code>childDao.blukUpdate(&quot;DELETE FROM &quot;+getEntityName()+&quot; WHERE id=?&quot;,child.getId())</code></div>
     *
     * @param primaryKey
     */
    public void delete(Serializable primaryKey);

    /**
     * Delete entity with lockMode.<br/>
     * 以指定的層級鎖定刪除物件
     *
     * @param primaryKey
     * @param lockMode
     */
    public void delete(Serializable primaryKey, String lockMode);

    public void delete(Collection<? extends Serializable> primaryKeys);

    public E remove(E entity);

    public E remove(E entity, String lockMode);

    public Collection<E> remove(Collection<E> entities);

    /**
     * Lock entity with lockMode.<br/>
     * 以指定的層級鎖定物件
     *
     * @param entity
     * @param lockMode
     * @return locked entity/鎖定的物件
     */
    public E lock(E entity, String lockMode);

    /**
     * Refresh the state of the instance from the database, overwriting changes
     * made to the entity, if any.<br/>
     * 重新讀取物件
     *
     * @param entity
     * @return entity instance/物件
     */
    public E refresh(E entity);

    /**
     * Refresh the state of the instance from the database, overwriting changes
     * made to the entity, if any.<br/>
     * 以指定的層級鎖定重新讀取物件
     *
     * @param entity
     * @param lockMode
     * @return entity instance/物件
     */
    public E refresh(E entity, String lockMode);
    
    public int sqlUpdate(String sql);
    
    public int sqlUpdate(String sql, Object... parameters);
    
    public int sqlUpdate(String sql, Map<String, ?> parameters);
    
    public List<Integer> sqlUpdate(List<String> sqls);

    public int bulkUpdate(String QL);

    public List<Integer> bulkUpdate(List<String> QLs);

    public int bulkUpdate(String QL, Object... parameters);

    public int bulkUpdate(String QL, Map<String, ?> parameters);

    /**
     * returne entity name.<br/>
     * 回傳ENTITY的名稱(類別名稱)<br/>
     * 叫用
     * {@link #getClazz() getClazz()}.{@link java.lang.Class#getName() getName()}
     *
     * @return 類別名稱
     */
    public String getEntityName();

    /**
     * alias for {@link #getEntityName() getEntityName()}.
     * {@link #getEntityName() getEntityName()}的別名
     *
     * @return 類別名稱
     */
    public String $e();

    /**
     * Default alias of entity.<br/>
     * Lowercase first character of class name.<br/>
     * 取得預設查詢所使用的別名<br/>
     * 實際上是等於 類別名稱的第一碼改為小寫
     *
     * @return alias name of target entity class
     */
    public String getAliasName();

    /**
     * alias for {@link #getAliasName() getAliasName()}.
     * {@link #getAliasName() getAliasName()}的別名
     *
     * @return alias name of target entity class
     */
    public String $a();

    /**
     * return {@link #$e() $e()}+&quot;AS&quot;+{@link #$a() $a()}.
     * 回傳{@link #$e() $e()}+&quot;AS&quot;+{@link #$a() $a()}的組合
     *
     * @return {@link #$e() $e()} AS {@link #$a() $a()}
     */
    public String $ea();

    /**
     * Query by criteria by invoke
     * {@link #findByCriteria(String ,int ,int ) findByCriteria(criteria, 0, 0)}.<br/>
     * 條件查詢<br/>會叫用{@link #findByCriteria(String ,int ,int ) findByCriteria(criteria, 0, 0)}
     *
     * @param qlCriteria QL 的查詢條件
     * @return List of entities. 物件集合
     */
    public List<E> findByCriteria(String qlCriteria);

    /**
     * Query by criteria.<br/>
     * QL statement will be &quot;from &quot; +
     * {@link #getEntityName() getEntityName()} + &quot; as &quot; +
     * {@link #getAliasName() getAliasName()} + criteria<br/>
     * 條件查詢所有資料<br/>
     * 實際查詢時QL會組成 &quot;from &quot; + {@link #getEntityName() getEntityName()} +
     * &quot; as &quot; + {@link #getAliasName() getAliasName()} + criteria
     *
     * @param qlCriteria QL parameter(using ? mark) 的查詢條件(參數要用 ? )
     * @param parameters paraemters(with ? mark). 參數(順序必須時應QL內的 ?)
     * @return List of entities.物件集合
     */
    public List<E> findByCriteria(String qlCriteria, Object... parameters);

    public List<E> findByCriteria(String qlCriteria, Map<String, ?> parameters);

    /**
     * Query by criteria.<br/>
     * QL statement will be &quot;from &quot; +
     * {@link #getEntityName() getEntityName()} + &quot; as &quot; +
     * {@link #getAliasName() getAliasName()} + criteria<br/>
     * 條件查詢所有資料<br/>
     * 實際查詢時QL會組成 &quot;from &quot; + {@link #getEntityName() getEntityName()} +
     * &quot; as &quot; + {@link #getAliasName() getAliasName()} + criteria
     *
     * @param qlCriteria QL (? mark means parameter).的查詢條件(參數要用 ? )
     * @param startPageNo start page no.起始頁數
     * @param pageSize page size.每頁筆數
     * @param parameters parameters of QL(follow by sequence.) .參數(順序必須時應QL內的 ?)
     * @return List of entities.物件集合
     */
    public List<E> findByCriteria(String qlCriteria, int startPageNo, int pageSize, Object... parameters);

    public List<E> findByCriteria(String qlCriteria, int startPageNo, int pageSize, Map<String, ?> parameters);

    /**
     * Query by criteria.<br/>
     * QL statement will be &quot;from &quot; +
     * {@link #getEntityName() getEntityName()} + &quot; as &quot; +
     * {@link #getAliasName() getAliasName()} + criteria<br/>
     * 條件查詢<br/>
     * 實際查詢時QL會組成 &quot;from &quot; + {@link #getEntityName() getEntityName()} +
     * &quot; as &quot; + {@link #getAliasName() getAliasName()} + criteria
     *
     * @param qlCriteria QL 的查詢條件
     * @param startPageNo start page no.起始頁數
     * @param pageSize page size. 每頁筆數
     * @return List of entities.物件集合
     */
    public List<E> findByCriteria(String qlCriteria, int startPageNo, int pageSize);

    public List<E> findByNamedQuery(String name);

    public List<E> findByNamedQuery(String name, Object... parameters);

    public List<E> findByNamedQuery(String name, Map<String, ?> parameters);

    /**
     * Query by SQL.<br/>
     * 以SQL Statement 取得查詢物件結果
     *
     * @param sql
     * @return List of entities.物件集合
     */
    public List<E> findBySQLQuery(String sql);

    public List<E> findBySQLQuery(String sql, Object... parameters);

    public List<E> findBySQLQuery(String sql, Map<String, ?> parameters);

    public <T> T findUniqueByQL(String QL);

    public <T> T findUniqueByQL(String QL, Object... parameters);

    public <T> T findUniqueByQL(String QL, Map<String, ?> parameters);

    public <T> List<T> findListByQL(String QL);

    public <T> List<T> findListByQL(String QL, Object... parameters);

    public <T> List<T> findListByQL(String QL, Map<String, ?> parameters);

    public <T> List<T> findListByNamedQuery(String name);

//    public <T> List<T> findListByNamedQuery(Class<T> clazz, String name);

    public <T> List<T> findListByNamedQuery(String name, Object... parameters);

//    public <T> List<T> findListByNamedQuery(Class<T> clazz, String name, Object... parameters);

    public <T> List<T> findListByNamedQuery(String name, Map<String, ?> parameters);

//    public <T> List<T> findListByNamedQuery(Class<T> clazz, String name, Map<String, ?> parameters);

    public E initLazyCollection(E entity, String collectionFieldName);
}
