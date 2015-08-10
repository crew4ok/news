package ru.uruydas.dao;

import ru.uruydas.model.news.News;
import ru.uruydas.model.users.User;
import ru.uruydas.model.subscriptions.Subscription;

import java.util.List;

public interface NewsDao {

    News getById(User currentUser, Long id);

    List<News> getLatestAll(User currentUser, int latestCount);

    List<News> getAllFromId(User currentUser, long id, int count);

    List<News> getLatestByUser(User user, int count);

    List<News> getByUserFromId(User user, Long id, int count);

    List<News> getLatestBySubscription(User currentUser, Subscription subscription, int count);

    List<News> getBySubscriptionFromId(User currentUser, Subscription subscription, Long id, int count);

    List<News> getLatestFavourites(User user, int count);

    List<News> getFavouritesFromId(User user, Long id, int count);

    List<News> getAllFavourites(User user);

    News create(User currentUser, News news);

    void favour(User user, News news);

    void unfavour(User user, News news);

    void like(User currentUser, News news);

    void dislike(User currentUser, News news);

    List<User> getLikers(News news);
}
