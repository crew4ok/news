package ru.uruydas.service.exception;

public class ImageRetrievalFailedException extends ImageOperationFailedException {
    public ImageRetrievalFailedException(String message) {
        super(message);
    }

    public ImageRetrievalFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
