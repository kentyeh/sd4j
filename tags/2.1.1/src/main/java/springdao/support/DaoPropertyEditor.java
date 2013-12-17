package springdao.support;

import java.beans.PropertyEditorSupport;
import org.springframework.util.StringUtils;
import springdao.RepositoryManager;

/**
 *
 * @author Kent Yeh
 */
public class DaoPropertyEditor extends PropertyEditorSupport {

    private RepositoryManager<?> daoManager;

    public DaoPropertyEditor(RepositoryManager<?> daoManager) {
        this.daoManager = daoManager;
    }

    public RepositoryManager<?> getDaoManager() {
        return daoManager;
    }

    @Override
    public String getAsText() {
        return getValue() == null ? "" : getValue().toString();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        try {
            setValue(StringUtils.hasText(text) ? daoManager.findByPrimaryKey(text) : null);
        } catch (RuntimeException e) {
            setValue(null);
        }
    }
}