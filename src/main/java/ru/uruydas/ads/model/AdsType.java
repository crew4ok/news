package ru.uruydas.ads.model;

public class AdsType {
    private final Long id;
    private final String name;
    private final Long adsCategoryId;

    public AdsType(Long id, String name, Long adsCategoryId) {
        this.id = id;
        this.name = name;
        this.adsCategoryId = adsCategoryId;
    }

    public AdsType(Long id) {
        this(id, null, null);
    }

    public AdsType(String name, Long adsCategoryId) {
        this(null, name, adsCategoryId);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getAdsCategoryId() {
        return adsCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdsType adsType = (AdsType) o;

        if (id != null ? !id.equals(adsType.id) : adsType.id != null) return false;
        if (name != null ? !name.equals(adsType.name) : adsType.name != null) return false;
        return !(adsCategoryId != null ? !adsCategoryId.equals(adsType.adsCategoryId) : adsType.adsCategoryId != null);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (adsCategoryId != null ? adsCategoryId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AdsType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", adsCategoryId=" + adsCategoryId +
                '}';
    }
}
