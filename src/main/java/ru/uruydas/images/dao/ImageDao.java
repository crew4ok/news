package ru.uruydas.images.dao;

import ru.uruydas.ads.model.Ads;
import ru.uruydas.comments.model.Comment;
import ru.uruydas.images.model.Image;
import ru.uruydas.news.model.News;
import ru.uruydas.users.model.User;

import java.util.List;
import java.util.Optional;

public interface ImageDao {

    Image getById(Long imageId);

    Image save(Image image);

    void linkToNews(Image image, News news, int ordering);

    List<Image> getByNews(News news);

    void linkToComment(Image image, Comment comment, int ordering);

    List<Image> getByComment(Comment comment);

    void linkToUser(Image image, User user);

    Optional<Image> getByUser(User user);

    void linkToAds(Image image, Ads ads, int ordering);

    List<Image> getByAds(Ads ads);
}
