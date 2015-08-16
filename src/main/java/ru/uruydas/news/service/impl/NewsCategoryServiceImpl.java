package ru.uruydas.news.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.uruydas.common.util.Validation;
import ru.uruydas.news.dao.NewsCategoryDao;
import ru.uruydas.news.model.NewsCategory;
import ru.uruydas.news.service.NewsCategoryService;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class NewsCategoryServiceImpl implements NewsCategoryService {

    @Autowired
    private NewsCategoryDao newsCategoryDao;

    @Override
    @Transactional(readOnly = false)
    public void create(NewsCategory newsCategory) {
        newsCategoryDao.create(newsCategory);
    }

    @Override
    public List<NewsCategory> getAll() {
        return newsCategoryDao.getAll();
    }

    @Override
    public NewsCategory getById(Long categoryId) {
        Validation.isGreaterThanZero(categoryId);

        return newsCategoryDao.getById(categoryId);
    }

}
