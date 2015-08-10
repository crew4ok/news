package ru.uruydas.service.exception;

public class ImageStoringFailedException extends ImageOperationFailedException {
    public ImageStoringFailedException(String message) {
        super(message);
    }

    public ImageStoringFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
