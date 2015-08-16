package ru.uruydas.news.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.uruydas.common.web.WebCommons;
import ru.uruydas.news.model.NewsCategory;
import ru.uruydas.news.service.NewsCategoryService;

import java.util.List;

@RestController
@RequestMapping(WebCommons.VERSION_PREFIX + "/news/news_category")
public class NewsCategoryController {

    @Autowired
    private NewsCategoryService newsCategoryService;

    @RequestMapping(method = RequestMethod.GET)
    public List<NewsCategory> getAll() {
        return newsCategoryService.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public NewsCategory getById(@PathVariable("id") Long categoryId) {
        return newsCategoryService.getById(categoryId);
    }

}
