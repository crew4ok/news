package ru.uruydas.common.dao.exception;

public class UpdateFailedException extends RuntimeException {
    public UpdateFailedException(String message) {
        super(message);
    }
}
