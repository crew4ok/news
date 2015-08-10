package ru.uruydas.web.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.uruydas.model.news.News;
import ru.uruydas.service.NewsService;
import ru.uruydas.web.common.WebCommons;

import java.util.List;

@RestController
@RequestMapping(WebCommons.VERSION_PREFIX + "/news/subscription")
public class SubscriptionNewsController {

    @Autowired
    private NewsService newsService;

    @RequestMapping(method = RequestMethod.GET)
    public List<News> getLatestSubscriptionNews() {
        return newsService.getLatestBySubscription(WebCommons.PAGING_COUNT);
    }

    @RequestMapping(method = RequestMethod.GET, params = "id")
    public List<News> getBySubscriptionFromId(@RequestParam("id") Long id) {
        return newsService.getBySubscriptionFromId(id, WebCommons.PAGING_COUNT);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.POST)
    public ResponseEntity<String> subscribe(@PathVariable("userId") Long userId) {
        newsService.subscribe(userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
