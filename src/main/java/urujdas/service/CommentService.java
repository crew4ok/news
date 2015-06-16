package urujdas.service;

import urujdas.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getAll(Long newsId);

    void create(Comment comment);

}
