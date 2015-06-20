package urujdas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urujdas.dao.NewsCategoryDao;
import urujdas.model.news.NewsCategory;
import urujdas.service.NewsCategoryService;
import urujdas.util.Validation;

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
