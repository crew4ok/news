package ru.uruydas.dao;

import ru.uruydas.model.comments.Comment;
import ru.uruydas.model.news.News;

import java.util.List;

public interface CommentDao {

    List<Comment> getAll(News news);

    Comment getById(Long id);

    Comment create(Comment comment);
}
