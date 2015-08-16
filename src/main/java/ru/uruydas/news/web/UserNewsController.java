package ru.uruydas.news.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.uruydas.common.web.WebCommons;
import ru.uruydas.news.model.News;
import ru.uruydas.news.service.NewsService;

import java.util.List;

@RestController
@RequestMapping(WebCommons.VERSION_PREFIX + "/news/user")
public class UserNewsController {

    @Autowired
    private NewsService newsService;

    @RequestMapping(method = RequestMethod.GET)
    public List<News> getLatestNewsByUser() {
        return newsService.getLatestByUser(WebCommons.PAGING_COUNT);
    }

    @RequestMapping(method = RequestMethod.GET, params = "id")
    public List<News> getNewsByUserFromId(@RequestParam("id") Long id) {
        return newsService.getByUserFromId(id, WebCommons.PAGING_COUNT);
    }
}
