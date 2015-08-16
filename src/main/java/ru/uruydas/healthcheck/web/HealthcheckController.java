package ru.uruydas.healthcheck.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.uruydas.common.web.WebCommons;
import ru.uruydas.healthcheck.service.HealthcheckService;

@RestController
@RequestMapping(WebCommons.VERSION_PREFIX + "/healthcheck")
public class HealthcheckController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HealthcheckController.class);

    @Autowired
    private HealthcheckService healthcheckService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> healthcheck() {
        try {
            healthcheckService.doHealthcheck();

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.warn("Healthcheck failed", e);

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
