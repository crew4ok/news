package ru.uruydas.model.ads;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import java.time.LocalDateTime;

public class Ads {
    private final Long id;
    private final String title;
    private final String description;
    private final AdsType adsType;
    private final LocalDateTime creationDate;
    private final String phone;
    private final String email;
    private final String city;
    private final AdsCategory adsCategory;

    @GeneratePojoBuilder
    public Ads(Long id, String title, String description, AdsType adsType, LocalDateTime creationDate, String phone,
               String email, String city, AdsCategory adsCategory) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.adsType = adsType;
        this.creationDate = creationDate;
        this.phone = phone;
        this.email = email;
        this.city = city;
        this.adsCategory = adsCategory;
    }

    public static AdsBuilder builder() {
        return new AdsBuilder();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public AdsType getAdsType() {
        return adsType;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public AdsCategory getAdsCategory() {
        return adsCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ads ads = (Ads) o;

        if (id != null ? !id.equals(ads.id) : ads.id != null) return false;
        if (title != null ? !title.equals(ads.title) : ads.title != null) return false;
        if (description != null ? !description.equals(ads.description) : ads.description != null) return false;
        if (adsType != null ? !adsType.equals(ads.adsType) : ads.adsType != null) return false;
        if (creationDate != null ? !creationDate.equals(ads.creationDate) : ads.creationDate != null) return false;
        if (phone != null ? !phone.equals(ads.phone) : ads.phone != null) return false;
        if (email != null ? !email.equals(ads.email) : ads.email != null) return false;
        if (city != null ? !city.equals(ads.city) : ads.city != null) return false;
        return !(adsCategory != null ? !adsCategory.equals(ads.adsCategory) : ads.adsCategory != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (adsType != null ? adsType.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (adsCategory != null ? adsCategory.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Ads{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", adsType=" + adsType +
                ", creationDate=" + creationDate +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", adsCategory=" + adsCategory +
                '}';
    }
}
