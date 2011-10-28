package springdao;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.persistence.EntityManagerFactory;

/**
 * Inject a {@link Dao} implemention  into {@link RepositoryManager}.<br/>
 * 將{@link Dao}注入{@link RepositoryManager}.<br/>
 * Usage(用法):<br/>
 * Initializate a {@link DaoAnnotationBeanPostProcessor} bean in your spring context.<br/>
 * 在您的Spring環境內建立一個{@link DaoAnnotationBeanPostProcessor} bean 就可以了.<br/>
 * <pre style="color:blue">
 *     &#64;Dao(value = Entity.class, name = "customDao")
 *     public DaoRepository<Entity> entityDao;
 * </pre>
 * <dl>
 * <dt style="color:red">Note(備註):</dt>
 * <dd>It's not recommend to use this annotation,
 * because of {@link DaoManager} may also create  the same name {@link DaoRepository}, and &#64;Dao can't  assure instantiation order 
 * of {@link DaoRepository}, please use carefully.<br/>
 *我建議不要使用此標記， 因為{@link DaoManager}也有可能建立同名的{@link DaoRepository}，而&#64;Dao並無法保證其建立順序，所以要用時，
 * 請自求多福。</dd>
 * </dl>
 * @author Kent Yeh
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Documented
@Inherited
public @interface Dao {

    /**
     * Class assoicated with {@link DaoRepository}.<br/>
     * {@link DaoRepository} 相關的一般化類別
     * @return 
     */
    Class value();

    /**
     * Deinfe {@link EntityManagerFactory} bean's name;<br/>
     * 設定 {@link EntityManagerFactory}  bean的名稱.
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
     * Auto register bean with ${@link #value() value}'s simple class name suffix with &quot;Dao&quot if not assigned ${@link #name() name}<br/>
     * 如果沒有指定${@link #name() name}，則自動以${@link #value() value}的類別名稱加上&quot;Dao&quot;註冊
     * @return 
     */
    boolean autoRegister() default true;
}
