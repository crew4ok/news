package ru.uruydas.news.service;

import ru.uruydas.news.model.NewsCategory;

import java.util.List;

public interface NewsCategoryService {

    void create(NewsCategory newsCategory);

    List<NewsCategory> getAll();

    NewsCategory getById(Long categoryId);

}
