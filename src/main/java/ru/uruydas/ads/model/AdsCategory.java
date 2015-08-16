package ru.uruydas.ads.model;

public class AdsCategory {
    private final Long id;
    private final String name;
    private final AdsCategory parentCategory;

    public AdsCategory(Long id, String name, AdsCategory parentCategory) {
        this.id = id;
        this.name = name;
        this.parentCategory = parentCategory;
    }

    public AdsCategory(String name) {
        this(null, name, null);
    }

    public AdsCategory(Long id) {
        this(id, null, null);
    }

    public AdsCategory(String name, AdsCategory category) {
        this(null, name, category);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AdsCategory getParentCategory() {
        return parentCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdsCategory that = (AdsCategory) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return !(parentCategory != null ? !parentCategory.equals(that.parentCategory) : that.parentCategory != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (parentCategory != null ? parentCategory.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AdsCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentCategory=" + parentCategory +
                '}';
    }
}
