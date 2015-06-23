package urujdas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urujdas.dao.ImageDao;
import urujdas.dao.NewsCategoryDao;
import urujdas.dao.NewsDao;
import urujdas.dao.SubscriptionDao;
import urujdas.model.favourites.FavourResult;
import urujdas.model.images.Image;
import urujdas.model.likes.LikeResult;
import urujdas.model.likes.LikeType;
import urujdas.model.news.News;
import urujdas.model.news.NewsCategory;
import urujdas.model.subscriptions.Subscription;
import urujdas.model.users.User;
import urujdas.service.NewsService;
import urujdas.service.UserService;
import urujdas.util.Validation;

import java.util.List;
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

        return constructNews(newsDao.getLatestAll(latestCount));
    }

    @Override
    public List<News> getAllFromId(long id, int count) {
        Validation.isGreaterThanZero(id);
        Validation.isGreaterThanZero(count);

        return constructNews(newsDao.getAllFromId(id, count));
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

        return constructNews(newsDao.getLatestBySubscription(subscription, count));
    }

    @Override
    public List<News> getBySubscriptionFromId(Long id, int count) {
        Validation.isGreaterThanZero(id);
        Validation.isGreaterThanZero(count);

        User currentUser = userService.getCurrentUser();
        Subscription subscription = subscriptionDao.getByUser(currentUser);

        return constructNews(newsDao.getBySubscriptionFromId(subscription, id, count));
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

                    return News.fromNews(n)
                            .withImageIds(imageIds)
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

        newsDao.create(news);

        if (news.getImageIds() != null) {
            for (int i = 0; i < news.getImageIds().size(); i++) {
                Long imageId = news.getImageIds().get(i);

                Image image = imageDao.getById(imageId);
                imageDao.linkToNews(image, news, i);
            }
        }

    }

    @Override
    @Transactional(readOnly = false)
    public FavourResult favour(Long newsId) {
        Validation.isGreaterThanZero(newsId);

        User currentUser = userService.getCurrentUser();
        News news = newsDao.getById(newsId);

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
        News news = newsDao.getById(newsId);

        List<User> likers = newsDao.getLikers(news);
        if (likers.contains(currentUser)) {
            newsDao.dislike(currentUser, news);

            return new LikeResult(LikeType.DISLIKE, likers.size() - 1);
        } else {
            newsDao.like(currentUser, news);

            return new LikeResult(LikeType.LIKE, likers.size() + 1);
        }
    }
}
