package ru.uruydas.ads.dao;

import ru.uruydas.model.ads.Ads;
import ru.uruydas.model.ads.AdsCategory;

import java.util.List;

public interface AdsCategoryDao {

    AdsCategory getById(Long id);

    AdsCategory getByAds(Ads ads);

    List<AdsCategory> getAllCategories();

    List<AdsCategory> getAllSubCategories(AdsCategory category);

    AdsCategory create(AdsCategory category);
}
