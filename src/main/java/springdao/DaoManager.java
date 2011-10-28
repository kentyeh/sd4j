package springdao;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.persistence.EntityManagerFactory;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Inject a {@link RepositoryManager} into a annotated {@link RepositoryManager}.<br/>
 * 注入{@link RepositoryManager} 到有注釋的{@link RepositoryManager}.<br/>
 * Usage(用法):<br/>
 * Initializate a {@link DaoAnnotationBeanPostProcessor} bean in your spring context.<br/>
 * 在您的Spring環境內建立一個{@link DaoAnnotationBeanPostProcessor} bean 就可以了.<br/>
 * <pre style="color:blue">
 *     &#64;DaoManager(Entity.class)
 *     public RepositoryManager<Entity> entityManager;
 * </pre>
 * Will inject and create a RepositoryManager implementation innto entityManager.<br/>
 * 將會建立一個RepositoryManager實例並插入 entityManager.
 * @author Kent Yeh
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Documented
@Inherited
public @interface DaoManager {

    /**
     * Class assoicated with {@link RepositoryManager}.<br/>
     * {@link RepositoryManager} 相關的一般化類別
     * @return 
     */
    Class value();

    /**
     * {@link PlatformTransactionManager TransactionManager} bean's reference name;<br/>
     * {@link PlatformTransactionManager TransactionManager}  bean的名稱.
     * @return 
     */
    String transactionManager() default "transactionManager";

    /**
     * Set whether to proxy the target class directly.<br/>
     * @return 
     */
    boolean proxyTargetClass() default false;

    /**
     * Because standard JPA does not support custom isolation levels,this value is ignored.<br/>
     * 因為標準的JPA不支援自定的isolation，所以忽略此屬性
     */
    String transactionAttributes() default "ignored";

    /**
     * {@link EntityManagerFactory} bean's reference name;<br/>
     * {@link EntityManagerFactory}  bean的名稱.
     * @return 
     */
    String factory() default "entityManagerFactory";

    /**
     * Naming object to be turned into a Spring bean in case of an autodetected component.<br/>
     * 註聞為Spring bean., 若已存在則取用，若不存在則建立
     * @return 
     */
    String name() default "";

    /**
     * Naming dao object to be turned into a Spring bean in case of an autodetected component.<br/>
     * 將 dao 註聞為Spring bean, 若已存在則取用，若不存在則建立
     * @return 
     */
    String daoName() default "";

    /**
     * 
     * @return 
     */
    Class<? extends RepositoryManager> baseManagerType() default RepositoryManager.class;

    /**
     * Auto register bean with {@link #value() value}'s simple class name suffix with &quot;Manager&quot if not assigned {@link #name() name},
     * also register dao bean with {@link #value() value}'s simple class name suffix with &quot;Dao&quot if not assigned {@link #daoName()  daoName}<br/>
     * 如果沒有指定{@link #name() name}，則自動以{@link #value() value}的類別名稱加上&quot;Manager&quot;註冊，
     * 同樣地，若未指定{@link #daoName()  daoName}，亦會以{@link #value() value}的類別名稱加上&quot;Dao&quot;註冊，
     * @return 
     */
    boolean autoRegister() default true;
}
