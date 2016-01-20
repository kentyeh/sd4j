package springdao.reposotory;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import springdao.RepositoryManager;

/**
 *
 * @author Kent Yeh
 */
@Transactional(readOnly = true)
public class CustomManager<E> extends RepositoryManager<E> {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void busiMethod() {

    }
}
