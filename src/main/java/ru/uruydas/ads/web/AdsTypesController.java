package ru.uruydas.ads.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.uruydas.ads.model.AdsType;
import ru.uruydas.ads.service.AdsService;
import ru.uruydas.common.web.WebCommons;

import java.util.List;

@RestController
@RequestMapping(WebCommons.VERSION_PREFIX + "/ads/categories/{categoryId}/types")
public class AdsTypesController {

    @Autowired
    private AdsService adsService;

    @RequestMapping(method = RequestMethod.GET)
    public List<AdsType> getAdsTypes(@PathVariable("categoryId") Long categoryId) {
        return adsService.getTypesByCategory(categoryId);
    }

}
