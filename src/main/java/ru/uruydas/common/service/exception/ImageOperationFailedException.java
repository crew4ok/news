package ru.uruydas.common.service.exception;

public abstract class ImageOperationFailedException extends RuntimeException {

    public ImageOperationFailedException(String message) {
        super(message);
    }

    public ImageOperationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
