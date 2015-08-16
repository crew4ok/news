package ru.uruydas.comments.service;

import ru.uruydas.comments.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getAll(Long newsId);

    void create(Comment comment);

}
