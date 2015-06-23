package urujdas.dao;

import urujdas.model.comments.Comment;
import urujdas.model.images.Image;
import urujdas.model.news.News;
import urujdas.model.users.User;

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
