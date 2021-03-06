package ru.uruydas.ads.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UpdateAdsRequest extends CreateAdsRequest {
    public UpdateAdsRequest(@JsonProperty("title") String title,
                            @JsonProperty("description") String description,
                            @JsonProperty("adsTypeId") Long adsTypeId,
                            @JsonProperty("phone") String phone,
                            @JsonProperty("email") String email,
                            @JsonProperty("city") String city,
                            @JsonProperty("price") Long price,
                            @JsonProperty("imageIds") List<Long> imageIds,
                            @JsonProperty("categoryId") Long categoryId) {
        super(title, description, adsTypeId, phone, email, city, price, imageIds, categoryId);
    }
}
