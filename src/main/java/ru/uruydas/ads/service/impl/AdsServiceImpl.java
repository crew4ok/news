package ru.uruydas.ads.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.uruydas.ads.dao.AdsCategoryDao;
import ru.uruydas.ads.dao.AdsDao;
import ru.uruydas.ads.service.AdsService;
import ru.uruydas.ads.service.exception.NotAnAuthorException;
import ru.uruydas.common.util.Validation;
import ru.uruydas.images.dao.ImageDao;
import ru.uruydas.images.model.Image;
import ru.uruydas.model.ads.Ads;
import ru.uruydas.model.ads.AdsCategory;
import ru.uruydas.users.model.User;
import ru.uruydas.users.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdsServiceImpl implements AdsService {
    @Autowired
    private AdsDao adsDao;

    @Autowired
    private AdsCategoryDao adsCategoryDao;

    @Autowired
    private ImageDao imageDao;

    @Autowired
    private UserService userService;

    @Override
    public List<AdsCategory> getAllCategories() {
        return adsCategoryDao.getAllCategories();
    }

    @Override
    public List<AdsCategory> getAllSubCategories(Long categoryId) {
        Validation.isGreaterThanZero(categoryId);

        AdsCategory category = adsCategoryDao.getById(categoryId);

        return adsCategoryDao.getAllSubCategories(category);
    }

    @Override
    public List<Ads> getLatestByCategory(Long categoryId, int count) {
        Validation.isGreaterThanZero(categoryId);
        Validation.isGreaterThanZero(count);

        AdsCategory category = adsCategoryDao.getById(categoryId);

        List<Ads> ads = adsDao.getLatestByCategory(category, count);
        return buildAds(ads);
    }

    @Override
    public List<Ads> getFromIdByCategory(Long categoryId, Long adsId, int count) {
        Validation.isGreaterThanZero(categoryId);
        Validation.isGreaterThanZero(adsId);
        Validation.isGreaterThanZero(count);

        AdsCategory category = adsCategoryDao.getById(categoryId);

        List<Ads> ads = adsDao.getFromIdByCategory(category, adsId, count);
        return buildAds(ads);
    }

    @Override
    public List<Ads> searchByTitle(String title) {
        Validation.isNotEmpty(title);

        List<Ads> ads = adsDao.searchByTitle(title);
        return buildAds(ads);
    }

    @Override
    public Ads getById(Long adsId) {
        Validation.isGreaterThanZero(adsId);

        Ads ads = adsDao.getById(adsId);
        return buildAds(ads);
    }

    @Override
    public Ads create(Ads ads) {
        // check if category exists
        adsCategoryDao.getById(ads.getAdsCategory().getId());

        User user = userService.getCurrentUser();
        ads = Ads.from(ads)
                .withAuthor(user)
                .build();

        // create ads
        Ads createdAds = Ads.from(adsDao.create(ads))
                .withImageIds(ads.getImageIds())
                .build();

        // link images
        return buildAds(linkImages(createdAds));
    }

    @Override
    public Ads update(Ads newAds) {
        Validation.isNotNull(newAds.getId());

        // check if category exists
        adsCategoryDao.getById(newAds.getAdsCategory().getId());

        // check if author is the same as the current user
        Ads oldAds = adsDao.getById(newAds.getId());
        User currentUser = userService.getCurrentUser();

        if (!oldAds.getAuthor().getId().equals(currentUser.getId())) {
            throw new NotAnAuthorException("Current user is not an author of the entity");
        }

        // update ads
        Ads updatedAds = Ads.from(adsDao.update(newAds))
                .withImageIds(newAds.getImageIds())
                .build();

        // update images
        return buildAds(linkImages(updatedAds));
    }

    private Ads linkImages(Ads ads) {
        List<Long> imageIds = ads.getImageIds();
        if (imageIds != null && !imageIds.isEmpty()) {
            for (int i = 0; i < imageIds.size(); i++) {
                Image image = imageDao.getById(imageIds.get(i));
                imageDao.linkToAds(image, ads, i);
            }
        }
        return Ads.from(ads)
                .withImageIds(imageIds)
                .build();
    }

    private List<Ads> buildAds(List<Ads> ads) {
        return ads.stream()
                .map(this::buildAds)
                .collect(Collectors.toList());
    }

    private Ads buildAds(Ads ads) {
        return Optional.ofNullable(ads)
                .map(this::insertImages)
                .map(this::insertCategory)
                .get();
    }

    private List<Ads> insertImages(List<Ads> ads) {
        return ads.stream()
                .map(this::insertImages)
                .collect(Collectors.toList());
    }

    private Ads insertImages(Ads ads) {
        List<Long> images = imageDao.getByAds(ads).stream()
                .map(Image::getId)
                .collect(Collectors.toList());

        User author = userService.attachImage(ads.getAuthor());

        return Ads.from(ads)
                .withAuthor(author)
                .withImageIds(images)
                .build();
    }

    private Ads insertCategory(Ads ads) {
        AdsCategory category = adsCategoryDao.getByAds(ads);

        return Ads.from(ads)
                .withAdsCategory(category)
                .build();
    }
}
