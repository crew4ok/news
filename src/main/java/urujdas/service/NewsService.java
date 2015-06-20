package urujdas.service;

import urujdas.model.likes.LikeResult;
import urujdas.model.news.FeedNews;
import urujdas.model.news.News;

import java.util.List;

public interface NewsService {

    List<FeedNews> getLatestAllLight(int latestCount);

    List<FeedNews> getLatestAll(int latestCount);

    List<FeedNews> getAllFromId(long id, int count);

    List<FeedNews> getLatestByUser(int count);

    List<FeedNews> getByUserFromId(Long id, int count);

    List<FeedNews> getLatestBySubscription(int count);

    List<FeedNews> getBySubscriptionFromId(Long id, int count);

    List<FeedNews> getLatestFavourites(int count);

    List<FeedNews> getFavouritesFromId(Long id, int count);

    void create(News news);

    void addNewsToFavourites(Long newsId);

    void subscribe(Long userId);

    LikeResult like(Long newsId);
}
