package ru.uruydas.common.web.exception;

import org.springframework.validation.Errors;

public class ValidationException extends RuntimeException {

    private final Errors errors;

    public ValidationException(Errors errors) {
        super("Validation error");
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}
