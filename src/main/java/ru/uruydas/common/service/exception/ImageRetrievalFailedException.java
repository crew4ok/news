package ru.uruydas.common.service.exception;

public class ImageRetrievalFailedException extends ImageOperationFailedException {
    public ImageRetrievalFailedException(String message) {
        super(message);
    }

    public ImageRetrievalFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
