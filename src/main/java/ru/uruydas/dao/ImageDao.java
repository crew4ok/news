package ru.uruydas.dao;

import ru.uruydas.model.comments.Comment;
import ru.uruydas.model.news.News;
import ru.uruydas.model.users.User;
import ru.uruydas.model.images.Image;

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
}
