package springdao.support;

import org.springframework.util.StringUtils;
import springdao.RepositoryManager;

/**
 *
 * @author Kent Yeh
 */
public class IntDaoPropertyEditor extends DaoPropertyEditor {

    public IntDaoPropertyEditor(RepositoryManager<?> daoManager) {
        super(daoManager);
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        try {
            setValue(StringUtils.hasText(text) ? getDaoManager().findByPrimaryKey(Integer.valueOf(text)) : null);
        } catch (RuntimeException e) {
            setValue(null);
        }
    }
}
