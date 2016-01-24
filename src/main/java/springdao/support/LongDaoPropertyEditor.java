package springdao.support;

import org.springframework.util.StringUtils;
import springdao.RepositoryManager;

/**
 *
 * @author Kent Yeh
 */
public class LongDaoPropertyEditor extends IntDaoPropertyEditor {

    public LongDaoPropertyEditor(RepositoryManager<?> daoManager) {
        super(daoManager);
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        try {
            setValue(StringUtils.hasText(text) ? getDaoManager().findByPrimaryKey(Long.valueOf(text)) : null);
        } catch (RuntimeException e) {
            setValue(null);
        }
    }
}
