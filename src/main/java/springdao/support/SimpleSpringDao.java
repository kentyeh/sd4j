package springdao.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kent Yeh
 */
public class SimpleSpringDao extends AbstractSpringDao {

    private static Logger logger = LoggerFactory.getLogger(SimpleSpringDao.class);
    public Class<?> clazz;

    public SimpleSpringDao(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }
}
