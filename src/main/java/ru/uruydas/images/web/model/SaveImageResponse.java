package ru.uruydas.images.web.model;

public class SaveImageResponse {
    private final Long id;

    public SaveImageResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
