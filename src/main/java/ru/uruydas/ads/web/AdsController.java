package ru.uruydas.ads.web;

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
import ru.uruydas.ads.model.Ads;
import ru.uruydas.ads.service.AdsService;
import ru.uruydas.ads.web.model.CreateAdsRequest;
import ru.uruydas.ads.web.model.UpdateAdsRequest;
import ru.uruydas.common.web.WebCommons;
import ru.uruydas.common.web.exception.ValidationException;

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

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateAds(@PathVariable("id") Long id,
                                            @RequestBody @Valid UpdateAdsRequest request,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        Ads ads = Ads.from(request.toModel())
                .withId(id)
                .build();

        adsService.update(ads);

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

    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public List<Ads> getLatestUserAds() {
        return adsService.getLatestUserAds(WebCommons.PAGING_COUNT);
    }

    @RequestMapping(value = "/my", method = RequestMethod.GET, params = { "adsId" })
    public List<Ads> getLatestUserAds(@RequestParam("adsId") Long adsId) {
        return adsService.getFromIdUserAds(adsId, WebCommons.PAGING_COUNT);
    }

    @RequestMapping(value = "/search/{title}", method = RequestMethod.GET)
    public List<Ads> searchByTitle(@PathVariable("title") String title) {
        return adsService.searchByTitle(title);
    }
}
