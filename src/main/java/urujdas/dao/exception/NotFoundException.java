package urujdas.dao.exception;

public class NotFoundException extends RuntimeException {

    private final Long id;
    private final Class<?> entityClass;

    public NotFoundException(Long id, Class<?> entityClass) {
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
