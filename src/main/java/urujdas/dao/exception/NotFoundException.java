package urujdas.dao.exception;

public class NotFoundException extends RuntimeException {
    private final Class<?> entityClass;
    private final Long id;

    public NotFoundException(Class<?> entityClass, Long id) {
        this.id = id;
        this.entityClass = entityClass;
    }

    public Long getId() {
        return id;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }
}
