package urujdas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urujdas.dao.NewsCategoryDao;
import urujdas.dao.NewsDao;
import urujdas.dao.SubscriptionDao;
import urujdas.model.Subscription;
import urujdas.model.likes.LikeResult;
import urujdas.model.likes.LikeType;
import urujdas.model.news.News;
import urujdas.model.news.NewsCategory;
import urujdas.model.news.FeedNews;
import urujdas.model.users.User;
import urujdas.service.NewsService;
import urujdas.service.UserService;
import urujdas.util.Validation;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public List<FeedNews> getLatestAllLight(int latestCount) {
        Validation.isGreaterThanZero(latestCount);

        User currentUser = userService.getCurrentUser();

        return newsDao.getLatestAll(currentUser, latestCount);
    }

    @Override
    public List<FeedNews> getLatestAll(int latestCount) {
        Validation.isGreaterThanZero(latestCount);

        User currentUser = userService.getCurrentUser();

        return newsDao.getLatestAll(currentUser, latestCount);
    }

    @Override
    public List<FeedNews> getAllFromId(long id, int count) {
        Validation.isGreaterThanZero(id);
        Validation.isGreaterThanZero(count);

        User currentUser = userService.getCurrentUser();

        return newsDao.getAllFromId(currentUser, id, count);
    }

    @Override
    public List<FeedNews> getLatestByUser(int count) {
        Validation.isGreaterThanZero(count);

        User currentUser = userService.getCurrentUser();

        return newsDao.getLatestByUser(currentUser, count);
    }

    @Override
    public List<FeedNews> getByUserFromId(Long id, int count) {
        Validation.isGreaterThanZero(count);

        User currentUser = userService.getCurrentUser();

        return newsDao.getByUserFromId(currentUser, id, count);
    }

    @Override
    public List<FeedNews> getLatestBySubscription(int count) {
        Validation.isGreaterThanZero(count);

        User currentUser = userService.getCurrentUser();
        Subscription subscription = subscriptionDao.getByUser(currentUser);

        return newsDao.getLatestBySubscription(currentUser, subscription, count);
    }

    @Override
    public List<FeedNews> getBySubscriptionFromId(Long id, int count) {
        Validation.isGreaterThanZero(id);
        Validation.isGreaterThanZero(count);

        User currentUser = userService.getCurrentUser();
        Subscription subscription = subscriptionDao.getByUser(currentUser);

        return newsDao.getBySubscriptionFromId(currentUser, subscription, id, count);
    }

    @Override
    public List<FeedNews> getLatestFavourites(int count) {
        Validation.isGreaterThanZero(count);

        User currentUser = userService.getCurrentUser();

        return newsDao.getLatestFavourites(currentUser, count);
    }

    @Override
    public List<FeedNews> getFavouritesFromId(Long id, int count) {
        Validation.isGreaterThanZero(id);
        Validation.isGreaterThanZero(count);

        User currentUser = userService.getCurrentUser();

        return newsDao.getFavouritesFromId(currentUser, id, count);
    }

    @Override
    @Transactional(readOnly = false)
    public void create(News news) {
        User currentUser = userService.getCurrentUser();
        NewsCategory newsCategory = newsCategoryDao.getById(news.getCategory().getId());

        news = News.fromNews(news)
                .withCreationDate(LocalDateTime.now(Clock.systemUTC()))
                .withAuthor(currentUser)
                .withCategory(newsCategory)
                .build();

        newsDao.create(news);
    }

    @Override
    @Transactional(readOnly = false)
    public void addNewsToFavourites(Long newsId) {
        User currentUser = userService.getCurrentUser();

        News news = newsDao.getById(newsId);

        newsDao.addToFavourites(currentUser, news);
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
