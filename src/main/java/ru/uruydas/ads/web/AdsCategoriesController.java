package ru.uruydas.ads.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.uruydas.ads.service.AdsService;
import ru.uruydas.common.web.WebCommons;
import ru.uruydas.model.ads.AdsCategory;

import java.util.List;

@RestController
@RequestMapping(WebCommons.VERSION_PREFIX + "/ads/categories")
public class AdsCategoriesController {

    @Autowired
    private AdsService adsService;

    @RequestMapping(method = RequestMethod.GET)
    public List<AdsCategory> getAllCategories() {
        return adsService.getAllCategories();
    }

    @RequestMapping(value = "/{id}/subcategories", method = RequestMethod.GET)
    public List<AdsCategory> getAllSubCategories(@PathVariable("id") Long categoryId) {
        return adsService.getAllSubCategories(categoryId);
    }
}
