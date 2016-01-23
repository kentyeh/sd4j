package springdao;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Inject a {@link RepositoryManager} into a annotated
 * {@link RepositoryManager}.<br/>
 * 注入{@link RepositoryManager} 到有注釋的{@link RepositoryManager}.<br/>
 * Usage(用法):<br/>
 * Initializate a {@link DaoAnnotationBeanPostProcessor} bean in your spring
 * context.<br/>
 * 在您的Spring環境內建立一個{@link DaoAnnotationBeanPostProcessor} bean 就可以了.<br/>
 * <pre style="color:blue">
 * &#64;DaoManager(Entity.class) public RepositoryManager<Entity> entityManager;
 * </pre> Will inject and create a RepositoryManager implementation innto
 * entityManager.<br/>
 * 將會建立一個RepositoryManager實例並插入 entityManager.
 *
 * @author Kent Yeh
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Documented
@Inherited
public @interface DaoManager {

    public static String LOCK_READ = "READ";
    public static String LOCK_WRITE = "WRITE";
    public static String LOCK_OPTIMISTIC = "OPTIMISTIC";
    public static String LOCK_OPTIMISTIC_FORCE_INCREMENT = "OPTIMISTIC_FORCE_INCREMENT";
    public static String LOCK_PESSIMISTIC_READ = "PESSIMISTIC_READ";
    public static String LOCK_PESSIMISTIC_WRITE = "PESSIMISTIC_WRITE";
    public static String LOCK_PESSIMISTIC_FORCE_INCREMENT = "PESSIMISTIC_FORCE_INCREMENT";
    public static String LOCK_NONE = "NONE";

    /**
     * Class assoicated with {@link RepositoryManager}.<br/>
     * {@link RepositoryManager} 相關的一般化類別
     *
     * @return name of Dao manager.
     */
    Class value() default Object.class;

    /**
     * Naming object to be turned into a Spring bean in case of an autodetected
     * component.<br/>
     * 註聞為Spring bean., 若已存在則取用，若不存在則建立
     *
     * @return name of spring bean.
     */
    String name() default "";

    /**
     * Naming dao object to be turned into a Spring bean in case of an
     * autodetected component.<br/>
     * 將 dao 註聞為Spring bean, 若已存在則取用，若不存在則建立
     *
     * @return name of dao spring bean.
     */
    String daoName() default "";

    /**
     *
     * @return {@link RepositoryManager RepositoryManager}
     */
    Class<? extends RepositoryManager> baseManagerType() default RepositoryManager.class;

    /**
     * Auto register bean with {@link #value() value}'s simple class name suffix
     * with &quot;Manager&quot if not assigned {@link #name() name}, also
     * register dao bean with {@link #value() value}'s simple class name suffix
     * with &quot;Dao&quot if not assigned {@link #daoName()  daoName}<br/>
     * 如果沒有指定{@link #name() name}，則自動以{@link #value() value}的類別名稱加上&quot;Manager&quot;註冊，
     * 同樣地，若未指定{@link #daoName()  daoName}，亦會以{@link #value() value}的類別名稱加上&quot;Dao&quot;註冊，
     *
     * @return spring bean's name
     */
    boolean autoRegister() default true;
}
