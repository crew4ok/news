package ru.uruydas.ads.service;

import ru.uruydas.ads.model.Ads;
import ru.uruydas.ads.model.AdsCategory;
import ru.uruydas.ads.model.AdsSearchCriteria;

import java.util.List;

public interface AdsService {
    List<AdsCategory> getAllCategories();

    List<AdsCategory> getAllSubCategories(Long categoryId);

    List<Ads> getLatestByCategory(Long categoryId, int count);

    List<Ads> getFromIdByCategory(Long categoryId, Long adsId, int count);

    List<Ads> getLatestUserAds(int count);

    List<Ads> getFromIdUserAds(Long id, int count);

    List<Ads> search(AdsSearchCriteria searchCriteria, int count);

    Ads getById(Long adsId);

    Ads create(Ads ads);

    Ads update(Ads ads);
}
