package urujdas.web.common.model.error;

public class NotFoundErrorResponse extends ErrorResponse {
    private final String resourceName;
    private final Object identifier;

    public NotFoundErrorResponse(String resourceName, Object identifier) {
        super(ErrorType.NOT_FOUND, "Resource was not found");

        this.identifier = identifier;
        this.resourceName = resourceName;
    }

    public Object getIdentifier() {
        return identifier;
    }

    public String getResourceName() {
        return resourceName;
    }
}
