package urujdas.web.common.model;

public class ErrorResponse {
    private final ErrorType errorType;
    private final String errorMessage;

    public ErrorResponse(ErrorType errorType, String errorMessage) {
        this.errorType = errorType;
        this.errorMessage = errorMessage;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
