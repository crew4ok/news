package urujdas.dao;

import urujdas.model.News;
import urujdas.model.NewsLight;
import urujdas.model.Subscription;
import urujdas.model.User;

import java.util.List;

public interface NewsDao {

    News getById(Long id);

    List<NewsLight> getLatestAllLight(User currentUser, int latestCount);

    List<News> getLatestAll(int latestCount);

    List<News> getAllFromId(long id, int count);

    List<News> getLatestByUser(User user, int count);

    List<News> getByUserFromId(User user, Long id, int count);

    List<News> getLatestBySubscription(Subscription subscription, int count);

    List<News> getBySubscriptionFromId(Subscription subscription, Long id, int count);

    List<News> getLatestFavourites(User user, int count);

    List<News> getFavouritesFromId(User user, Long id, int count);

    News create(News news);

    void addToFavourites(User user, News news);

    void like(User currentUser, News news);

    void dislike(User currentUser, News news);

    List<User> getLikers(News news);
}
