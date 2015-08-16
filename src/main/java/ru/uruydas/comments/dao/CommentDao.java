package ru.uruydas.comments.dao;

import ru.uruydas.comments.model.Comment;
import ru.uruydas.news.model.News;

import java.util.List;

public interface CommentDao {

    List<Comment> getAll(News news);

    Comment getById(Long id);

    Comment create(Comment comment);
}
