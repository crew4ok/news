package ru.uruydas.web.ads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.uruydas.model.ads.AdsCategory;
import ru.uruydas.service.AdsService;
import urujdas.web.common.WebCommons;

import java.util.List;

@RestController
@RequestMapping(WebCommons.VERSION_PREFIX + "/ads/categories")
public class AdsCategoriesController {

    @Autowired
    private AdsService adsService;

    @RequestMapping(method = RequestMethod.GET)
    public List<AdsCategory> getAll() {
        return adsService.getAllCategories();
    }
}
