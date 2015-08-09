package ru.uruydas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.uruydas.dao.AdsCategoryDao;
import ru.uruydas.dao.AdsDao;
import ru.uruydas.model.ads.Ads;
import ru.uruydas.service.AdsService;
import ru.uruydas.model.ads.AdsCategory;
import urujdas.util.Validation;

import java.util.List;

@Service
public class AdsServiceImpl implements AdsService {
    @Autowired
    private AdsDao adsDao;

    @Autowired
    private AdsCategoryDao adsCategoryDao;

    @Override
    public List<AdsCategory> getAllCategories() {
        return adsCategoryDao.getAll();
    }

    @Override
    public List<Ads> getLatestByCategory(Long categoryId, int count) {
        Validation.isGreaterThanZero(categoryId);
        Validation.isGreaterThanZero(count);

        AdsCategory category = adsCategoryDao.getById(categoryId);

        return adsDao.getLatestByCategory(category, count);
    }

    @Override
    public List<Ads> getFromIdByCategory(Long categoryId, Long adsId, int count) {
        Validation.isGreaterThanZero(categoryId);
        Validation.isGreaterThanZero(adsId);
        Validation.isGreaterThanZero(count);

        AdsCategory category = adsCategoryDao.getById(categoryId);

        return adsDao.getFromIdByCategory(category, adsId, count);
    }

    @Override
    public List<Ads> searchByTitle(String title) {
        Validation.isNotEmpty(title);

        return adsDao.searchByTitle(title);
    }

    @Override
    public Ads getById(Long adsId) {
        Validation.isGreaterThanZero(adsId);

        return adsDao.getById(adsId);
    }

    @Override
    public void create(Ads ads) {
        // check if category exists
        adsCategoryDao.getById(ads.getAdsCategory().getId());

        adsDao.create(ads);
    }

    @Override
    public void update(Ads ads) {
        Validation.isNotNull(ads.getId());

        adsDao.update(ads);
    }
}
