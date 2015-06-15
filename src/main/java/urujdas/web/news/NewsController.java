package urujdas.web.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import urujdas.model.News;
import urujdas.service.NewsService;
import urujdas.web.common.WebCommons;
import urujdas.web.exception.ValidationException;
import urujdas.web.news.model.CreateNewsRequest;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(WebCommons.VERSION_PREFIX + "/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @RequestMapping(method = RequestMethod.GET)
    public List<News> getLatestFromAllNews() {
        return newsService.getLatestAll(WebCommons.PAGING_COUNT);
    }

    @RequestMapping(method = RequestMethod.GET, params = "id")
    public List<News> getAllFromId(@RequestParam("id") Long id) {
        return newsService.getAllFromId(id, WebCommons.PAGING_COUNT);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> createNewNews(@Valid @RequestBody CreateNewsRequest request,
                                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        newsService.create(request.toNews());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/like/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> likeNews(@PathVariable("id") Long newsId) {
        newsService.like(newsId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
