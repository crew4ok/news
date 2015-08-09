package ru.uruydas.dao;

import ru.uruydas.model.ads.Ads;
import ru.uruydas.model.ads.AdsCategory;

import java.util.List;

public interface AdsDao {

    Ads getById(Long id);

    List<Ads> getLatestByCategory(AdsCategory category, int count);

    List<Ads> getFromIdByCategory(AdsCategory category, Long adsId, int count);

    List<Ads> searchByTitle(String title);

    Ads create(Ads ads);

    Ads update(Ads ads);
}
