package ru.uruydas.common.service.exception;

import java.time.LocalDateTime;

public class PullUpTooFrequentException extends RuntimeException {
    private final LocalDateTime lastPullUp;

    public PullUpTooFrequentException(LocalDateTime lastPullUp) {
        this.lastPullUp = lastPullUp;
    }

    public LocalDateTime getLastPullUp() {
        return lastPullUp;
    }
}
