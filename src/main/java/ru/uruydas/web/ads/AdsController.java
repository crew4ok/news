package ru.uruydas.web.ads;

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
import ru.uruydas.model.ads.Ads;
import ru.uruydas.web.ads.model.CreateAdsRequest;
import ru.uruydas.service.AdsService;
import urujdas.web.common.WebCommons;
import urujdas.web.common.exception.ValidationException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(WebCommons.VERSION_PREFIX + "/ads")
public class AdsController {

    @Autowired
    private AdsService adsService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> createNewAds(@RequestBody @Valid CreateAdsRequest request,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        adsService.create(request.toModel());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Ads getById(@PathVariable("id") Long adsId) {
        return adsService.getById(adsId);
    }

    @RequestMapping(method = RequestMethod.GET, params = { "categoryId" })
    public List<Ads> getLatestByCategory(@RequestParam("categoryId") Long categoryId) {
        return adsService.getLatestByCategory(categoryId, WebCommons.PAGING_COUNT);
    }

    @RequestMapping(method = RequestMethod.GET, params = { "categoryId", "adsId" })
    public List<Ads> getFromIdByCategory(@RequestParam("categoryId") Long categoryId,
                                         @RequestParam("adsId") Long adsId) {
        return adsService.getFromIdByCategory(categoryId, adsId, WebCommons.PAGING_COUNT);
    }
}
