package urujdas.dao;

import urujdas.model.comments.Comment;
import urujdas.model.news.News;

import java.util.List;

public interface CommentDao {

    List<Comment> getAll(News news);

    Comment getById(Long id);

    Comment create(Comment comment);
}
