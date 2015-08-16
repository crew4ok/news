package ru.uruydas.dating.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.uruydas.common.web.WebCommons;
import ru.uruydas.common.web.exception.ValidationException;
import ru.uruydas.dating.service.DatingService;
import ru.uruydas.users.model.User;
import ru.uruydas.users.model.UserFilter;
import ru.uruydas.users.web.model.UserFilterRequest;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(WebCommons.VERSION_PREFIX + "/users/dating")
public class DatingController {

    @Autowired
    private DatingService datingService;

    @RequestMapping(method = RequestMethod.POST)
    public List<User> getLatestUsersByFilter(@RequestBody @Valid UserFilterRequest userFilter,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        if (userFilter.getPullUpDate() == null) {
            return datingService.getLatestUsersByFilter(
                    userFilter.toUserFilter(),
                    WebCommons.PAGING_COUNT
            );
        } else {
            return datingService.getUsersByFilterFromDate(
                    userFilter.toUserFilter(),
                    userFilter.getPullUpDate(),
                    WebCommons.PAGING_COUNT
            );
        }

    }

    @RequestMapping(value = "/last_filter", method = RequestMethod.GET)
    public ResponseEntity<UserFilter> getLastFilter() {
        UserFilter currentUserFilter = datingService.findCurrentUserFilter();

        if (currentUserFilter != null) {
            return new ResponseEntity<>(currentUserFilter, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value = "/pull_up/", method = RequestMethod.POST)
    public ResponseEntity<String> pullUp() {
        datingService.pullCurrentUserUp();

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
