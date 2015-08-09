package ru.uruydas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.uruydas.dao.AdsCategoryDao;
import ru.uruydas.dao.AdsDao;
import ru.uruydas.dao.ImageDao;
import ru.uruydas.model.ads.Ads;
import ru.uruydas.model.ads.AdsCategory;
import ru.uruydas.model.images.Image;
import ru.uruydas.model.users.User;
import ru.uruydas.service.AdsService;
import ru.uruydas.service.UserService;
import ru.uruydas.service.exception.NotAnAuthorException;
import ru.uruydas.util.Validation;

import java.util.List;
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
        return adsCategoryDao.getAll();
    }

    @Override
    public List<Ads> getLatestByCategory(Long categoryId, int count) {
        Validation.isGreaterThanZero(categoryId);
        Validation.isGreaterThanZero(count);

        AdsCategory category = adsCategoryDao.getById(categoryId);

        List<Ads> ads = adsDao.getLatestByCategory(category, count);
        return insertImages(ads);
    }

    @Override
    public List<Ads> getFromIdByCategory(Long categoryId, Long adsId, int count) {
        Validation.isGreaterThanZero(categoryId);
        Validation.isGreaterThanZero(adsId);
        Validation.isGreaterThanZero(count);

        AdsCategory category = adsCategoryDao.getById(categoryId);

        List<Ads> ads = adsDao.getFromIdByCategory(category, adsId, count);
        return insertImages(ads);
    }

    @Override
    public List<Ads> searchByTitle(String title) {
        Validation.isNotEmpty(title);

        List<Ads> ads = adsDao.searchByTitle(title);
        return insertImages(ads);
    }

    @Override
    public Ads getById(Long adsId) {
        Validation.isGreaterThanZero(adsId);

        Ads ads = adsDao.getById(adsId);
        return insertImages(ads);
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
        Ads createdAds = adsDao.create(ads);

        // link images
        return linkImages(createdAds);
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
        Ads updatedAds = adsDao.update(newAds);

        // update images
        return linkImages(updatedAds);
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
}
