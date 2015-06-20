package urujdas.service;

import urujdas.model.news.NewsCategory;

import java.util.List;

public interface NewsCategoryService {

    void create(NewsCategory newsCategory);

    List<NewsCategory> getAll();

    NewsCategory getById(Long categoryId);

}
