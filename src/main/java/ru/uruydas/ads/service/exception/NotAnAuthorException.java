package ru.uruydas.ads.service.exception;

public class NotAnAuthorException extends RuntimeException {
    public NotAnAuthorException(String message) {
        super(message);
    }
}
