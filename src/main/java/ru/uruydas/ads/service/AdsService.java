package ru.uruydas.ads.service;

import ru.uruydas.model.ads.Ads;
import ru.uruydas.model.ads.AdsCategory;

import java.util.List;

public interface AdsService {
    List<AdsCategory> getAllCategories();

    List<AdsCategory> getAllSubCategories(Long categoryId);

    List<Ads> getLatestByCategory(Long categoryId, int count);

    List<Ads> getFromIdByCategory(Long categoryId, Long adsId, int count);

    List<Ads> searchByTitle(String title);

    Ads getById(Long adsId);

    Ads create(Ads ads);

    Ads update(Ads ads);
}
