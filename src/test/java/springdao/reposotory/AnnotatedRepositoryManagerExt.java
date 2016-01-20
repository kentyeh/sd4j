package springdao.reposotory;

import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Kent Yeh
 */
@Transactional(readOnly = true)
public class AnnotatedRepositoryManagerExt<E> extends AnnotatedRepositoryManager<E> {
}
