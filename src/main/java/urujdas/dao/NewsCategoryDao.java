package urujdas.dao;

import urujdas.model.news.NewsCategory;

import java.util.List;

public interface NewsCategoryDao {

    NewsCategory create(NewsCategory category);

    NewsCategory getById(Long categoryId);

    List<NewsCategory> getAll();
}
