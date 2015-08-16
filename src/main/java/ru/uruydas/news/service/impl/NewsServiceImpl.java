package ru.uruydas.news.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.uruydas.common.util.Validation;
import ru.uruydas.favourites.model.FavourResult;
import ru.uruydas.images.dao.ImageDao;
import ru.uruydas.images.model.Image;
import ru.uruydas.likes.model.LikeResult;
import ru.uruydas.likes.model.LikeType;
import ru.uruydas.news.dao.NewsCategoryDao;
import ru.uruydas.news.dao.NewsDao;
import ru.uruydas.news.model.News;
import ru.uruydas.news.model.NewsCategory;
import ru.uruydas.news.service.NewsService;
import ru.uruydas.subscriptions.dao.SubscriptionDao;
import ru.uruydas.subscriptions.model.Subscription;
import ru.uruydas.users.model.User;
import ru.uruydas.users.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class NewsServiceImpl implements NewsService {

    @Autowired
    private UserService userService;

    @Autowired
    private NewsDao newsDao;

    @Autowired
    private SubscriptionDao subscriptionDao;

    @Autowired
    private NewsCategoryDao newsCategoryDao;

    @Autowired
    private ImageDao imageDao;

    @Override
    public List<News> getLatestAll(int latestCount) {
        Validation.isGreaterThanZero(latestCount);

        return constructNews(newsDao.getLatestAll(userService.getCurrentUser(), latestCount));
    }

    @Override
    public List<News> getAllFromId(long id, int count) {
        Validation.isGreaterThanZero(id);
        Validation.isGreaterThanZero(count);

        return constructNews(newsDao.getAllFromId(userService.getCurrentUser(), id, count));
    }

    @Override
    public List<News> getLatestByUser(int count) {
        Validation.isGreaterThanZero(count);

        User currentUser = userService.getCurrentUser();

        return constructNews(newsDao.getLatestByUser(currentUser, count));
    }

    @Override
    public List<News> getByUserFromId(Long id, int count) {
        Validation.isGreaterThanZero(count);

        User currentUser = userService.getCurrentUser();

        return constructNews(newsDao.getByUserFromId(currentUser, id, count));
    }

    @Override
    public List<News> getLatestBySubscription(int count) {
        Validation.isGreaterThanZero(count);

        User currentUser = userService.getCurrentUser();
        Subscription subscription = subscriptionDao.getByUser(currentUser);

        return constructNews(newsDao.getLatestBySubscription(currentUser, subscription, count));
    }

    @Override
    public List<News> getBySubscriptionFromId(Long id, int count) {
        Validation.isGreaterThanZero(id);
        Validation.isGreaterThanZero(count);

        User currentUser = userService.getCurrentUser();
        Subscription subscription = subscriptionDao.getByUser(currentUser);

        return constructNews(newsDao.getBySubscriptionFromId(currentUser, subscription, id, count));
    }

    @Override
    public List<News> getLatestFavourites(int count) {
        Validation.isGreaterThanZero(count);

        User currentUser = userService.getCurrentUser();

        return constructNews(newsDao.getLatestFavourites(currentUser, count));
    }

    @Override
    public List<News> getFavouritesFromId(Long id, int count) {
        Validation.isGreaterThanZero(id);
        Validation.isGreaterThanZero(count);

        User currentUser = userService.getCurrentUser();

        return constructNews(newsDao.getFavouritesFromId(currentUser, id, count));
    }

    private List<News> constructNews(List<News> originalNews) {
        return originalNews.stream()
                .map(n -> {
                    List<Image> images = imageDao.getByNews(n);
                    List<Long> imageIds = images.stream()
                            .map(Image::getId)
                            .collect(Collectors.toList());

                    User author = n.getAuthor();
                    Optional<Image> userImage = imageDao.getByUser(author);
                    if (userImage.isPresent()) {
                        author = User.fromUser(author)
                                .withImageId(userImage.get().getId())
                                .build();
                    }

                    return News.fromNews(n)
                            .withImageIds(imageIds)
                            .withAuthor(author)
                            .build();
                }).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = false)
    public void create(News news) {
        User currentUser = userService.getCurrentUser();
        NewsCategory newsCategory = newsCategoryDao.getById(news.getCategory().getId());

        news = News.fromNews(news)
                .withAuthor(currentUser)
                .withCategory(newsCategory)
                .build();

        News createdNews = newsDao.create(currentUser, news);

        if (news.getImageIds() != null) {
            for (int i = 0; i < news.getImageIds().size(); i++) {
                Long imageId = news.getImageIds().get(i);

                Image image = imageDao.getById(imageId);
                imageDao.linkToNews(image, createdNews, i);
            }
        }

    }

    @Override
    @Transactional(readOnly = false)
    public FavourResult favour(Long newsId) {
        Validation.isGreaterThanZero(newsId);

        User currentUser = userService.getCurrentUser();
        News news = newsDao.getById(currentUser, newsId);

        List<News> favourites = newsDao.getAllFavourites(currentUser);
        if (favourites.contains(news)) {
            newsDao.unfavour(currentUser, news);

            return FavourResult.UNFAVOUR;
        } else {
            newsDao.favour(currentUser, news);

            return FavourResult.FAVOUR;
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void subscribe(Long userId) {
        User currentUser = userService.getCurrentUser();
        User author = userService.getById(userId);

        subscriptionDao.create(currentUser, author);
    }

    @Override
    @Transactional(readOnly = false)
    public LikeResult like(Long newsId) {
        Validation.isGreaterThanZero(newsId);

        User currentUser = userService.getCurrentUser();
        News news = newsDao.getById(currentUser, newsId);

        List<User> likers = newsDao.getLikers(news);
        boolean liked = likers.stream()
                .anyMatch(u -> u.getId().equals(currentUser.getId()));

        if (liked) {
            newsDao.dislike(currentUser, news);

            return new LikeResult(LikeType.DISLIKE, likers.size() - 1);
        } else {
            newsDao.like(currentUser, news);

            return new LikeResult(LikeType.LIKE, likers.size() + 1);
        }
    }
}
