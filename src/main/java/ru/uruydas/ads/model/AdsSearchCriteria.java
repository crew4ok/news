package ru.uruydas.ads.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class AdsSearchCriteria {
    private final Optional<String> title;
    private final Optional<String> city;
    private final Optional<Long> categoryId;
    private final Optional<Long> adsId;

    @JsonCreator
    public AdsSearchCriteria(@JsonProperty("title") Optional<String> title,
                             @JsonProperty("city")  Optional<String> city,
                             @JsonProperty("categoryId") Optional<Long> categoryId,
                             @JsonProperty("adsId") Optional<Long> adsId) {
        this.title = title;
        this.city = city;
        this.categoryId = categoryId;
        this.adsId = adsId;
    }

    public Optional<String> getTitle() {
        return title;
    }

    public Optional<String> getCity() {
        return city;
    }

    public Optional<Long> getCategoryId() {
        return categoryId;
    }

    public Optional<Long> getAdsId() {
        return adsId;
    }
}
