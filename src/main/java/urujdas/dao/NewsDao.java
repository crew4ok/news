package urujdas.dao;

import urujdas.model.Subscription;
import urujdas.model.news.News;
import urujdas.model.news.FeedNews;
import urujdas.model.users.User;

import java.util.List;

public interface NewsDao {

    News getById(Long id);

    List<User> getLikers(News news);

    // -- all news -- //
    List<FeedNews> getLatestAll(User currentUser, int latestCount);

    List<FeedNews> getAllFromId(User currentUser, long id, int count);

    // -- user news -- //
    List<FeedNews> getLatestByUser(User user, int count);

    List<FeedNews> getByUserFromId(User user, Long id, int count);

    // -- subscription news -- //
    List<FeedNews> getLatestBySubscription(User currentUser, Subscription subscription, int count);

    List<FeedNews> getBySubscriptionFromId(User currentUser, Subscription subscription, Long id, int count);

    // -- favourite news -- //
    List<FeedNews> getLatestFavourites(User user, int count);

    List<FeedNews> getFavouritesFromId(User user, Long id, int count);

    // -- mutators -- //
    News create(News news);

    void addToFavourites(User user, News news);

    void like(User currentUser, News news);

    void dislike(User currentUser, News news);
}
