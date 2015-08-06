package ru.uruydas.model.ads;

public class AdsCategory {
    private final Long id;
    private final String name;

    public AdsCategory(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public AdsCategory(Long id) {
        this(id, null);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
