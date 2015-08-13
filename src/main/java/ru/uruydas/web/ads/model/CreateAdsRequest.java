package ru.uruydas.web.ads.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.uruydas.model.ads.Ads;
import ru.uruydas.model.ads.AdsCategory;
import ru.uruydas.model.ads.AdsType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

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

    @Min(0)
    private final Long price;

    private final List<Long> imageIds;

    @NotNull
    private final Long categoryId;

    @JsonCreator
    public CreateAdsRequest(@JsonProperty("title") String title,
                            @JsonProperty("description") String description,
                            @JsonProperty("type") AdsType adsType,
                            @JsonProperty("phone") String phone,
                            @JsonProperty("email") String email,
                            @JsonProperty("city") String city,
                            @JsonProperty("price") Long price,
                            @JsonProperty("imageIds") List<Long> imageIds,
                            @JsonProperty("categoryId") Long categoryId) {
        this.title = title;
        this.description = description;
        this.adsType = adsType;
        this.phone = phone;
        this.email = email;
        this.city = city;
        this.price = price;
        this.imageIds = imageIds;
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

    public Long getPrice() {
        return price;
    }

    public List<Long> getImageIds() {
        return imageIds;
    }

    public Long getcategoryId() {
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
                .withPrice(price)
                .withImageIds(imageIds)
                .withAdsCategory(new AdsCategory(categoryId))
                .build();
    }
}
