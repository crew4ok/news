package ru.uruydas.web.ads.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.uruydas.model.ads.Ads;
import ru.uruydas.model.ads.AdsCategory;
import ru.uruydas.model.ads.AdsType;

import javax.validation.constraints.NotNull;

public class CreateAdsRequest {
    @NotNull
    private final String title;

    @NotNull
    private final String description;

    @NotNull
    private final AdsType adsType;

    private final String phone;
    private final String email;
    private final String city;

    @NotNull
    private final Long categoryId;

    @JsonCreator
    public CreateAdsRequest(@JsonProperty("title") String title,
                            @JsonProperty("description") String description,
                            @JsonProperty("type") AdsType adsType,
                            @JsonProperty("phone") String phone,
                            @JsonProperty("email") String email,
                            @JsonProperty("city") String city,
                            @JsonProperty("categoryId") Long categoryId) {
        this.title = title;
        this.description = description;
        this.adsType = adsType;
        this.phone = phone;
        this.email = email;
        this.city = city;
        this.categoryId = categoryId;
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

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Ads toModel() {
        return Ads.builder()
                .withTitle(title)
                .withDescription(description)
                .withAdsType(adsType)
                .withPhone(phone)
                .withEmail(email)
                .withCity(city)
                .withAdsCategory(new AdsCategory(categoryId))
                .build();
    }
}
