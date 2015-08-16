package ru.uruydas.ads.dao;

import ru.uruydas.ads.model.Ads;
import ru.uruydas.ads.model.AdsCategory;
import ru.uruydas.users.model.User;

import java.util.List;

public interface AdsDao {

    Ads getById(Long id);

    List<Ads> getLatestByCategory(AdsCategory category, int count);

    List<Ads> getFromIdByCategory(AdsCategory category, Long adsId, int count);

    List<Ads> getLatestUserAds(User currentUser, int count);

    List<Ads> getFromIdUserAds(User currentUser, Long id, int count);

    List<Ads> searchByTitle(String title);

    Ads create(Ads ads);

    Ads update(Ads ads);
}
