package urujdas.web.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import urujdas.model.news.News;
import urujdas.service.NewsService;
import urujdas.web.common.WebCommons;

import java.util.List;

@RestController
@RequestMapping(WebCommons.VERSION_PREFIX + "/news/favourites")
public class FavouritesNewsController {

    @Autowired
    private NewsService newsService;

    @RequestMapping(method = RequestMethod.GET)
    public List<News> getLatestFavourites() {
        return newsService.getLatestFavourites(WebCommons.PAGING_COUNT);
    }

    @RequestMapping(method = RequestMethod.GET, params = "id")
    public List<News> getFavouritesFromId(@RequestParam("id") Long id) {
        return newsService.getFavouritesFromId(id, WebCommons.PAGING_COUNT);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> addNewsToFavourites(@PathVariable("id") Long id) {
        newsService.addNewsToFavourites(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
