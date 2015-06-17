package urujdas.dao;

import urujdas.model.Comment;
import urujdas.model.News;

import java.util.List;

public interface CommentDao {

    List<Comment> getAll(News news);

    Comment getById(Long id);

    Comment create(Comment comment);
}