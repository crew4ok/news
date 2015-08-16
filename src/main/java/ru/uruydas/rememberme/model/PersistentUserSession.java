package ru.uruydas.rememberme.model;

public class PersistentUserSession {
    private final Long id;
    private final String token;
    private final Long userId;

    public PersistentUserSession(Long id, String token, Long userId) {
        this.id = id;
        this.token = token;
        this.userId = userId;
    }

    public PersistentUserSession(String token, Long userId) {
        this(null, token, userId);
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public Long getUserId() {
        return userId;
    }
}
