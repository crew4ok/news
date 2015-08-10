package ru.uruydas.web.common.model.error;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidationErrorResponse extends ErrorResponse {

    private final Map<String, List<String>> validationErrors;

    public ValidationErrorResponse(Errors errors) {
        super(ErrorType.VALIDATION_ERROR, "Validation failed");

        this.validationErrors = constructValidationErrorsMap(errors);
    }

    public Map<String, List<String>> getValidationErrors() {
        return validationErrors;
    }

    private Map<String, List<String>> constructValidationErrorsMap(Errors errors) {
        Map<String, List<String>> errorsMap = new HashMap<>();

        for (FieldError fieldError : errors.getFieldErrors()) {
            String fieldName = fieldError.getField();

            List<String> fieldErrors = errorsMap.get(fieldName);
            if (fieldErrors == null) {
                fieldErrors = new ArrayList<>();
                errorsMap.put(fieldName, fieldErrors);
            }

            fieldErrors.add(fieldError.getDefaultMessage());
        }

        return errorsMap;
    }
}
