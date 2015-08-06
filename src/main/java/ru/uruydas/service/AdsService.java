package ru.uruydas.service;

import ru.uruydas.model.ads.Ads;
import ru.uruydas.model.ads.AdsCategory;

import java.util.List;

public interface AdsService {
    List<AdsCategory> getAllCategories();

    List<Ads> getLatestByCategory(Long categoryId, int count);

    List<Ads> getFromIdByCategory(Long categoryId, Long adsId, int count);

    Ads getById(Long adsId);

    void create(Ads ads);
}
