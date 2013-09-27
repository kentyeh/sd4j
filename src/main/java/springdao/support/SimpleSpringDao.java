package springdao.support;

/**
 *
 * @author Kent Yeh
 */
public class SimpleSpringDao extends AbstractSpringDao {

    public Class<?> clazz;

    public SimpleSpringDao(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Class getClazz() {
        return clazz;
    }
}
