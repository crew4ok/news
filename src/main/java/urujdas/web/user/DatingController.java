package urujdas.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import urujdas.model.users.User;
import urujdas.service.DatingService;
import urujdas.web.common.WebCommons;
import urujdas.web.exception.ValidationException;
import urujdas.web.user.model.UserFilterRequest;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(WebCommons.VERSION_PREFIX + "/users/dating")
public class DatingController {

    @Autowired
    private DatingService datingService;

    @RequestMapping(method = RequestMethod.POST)
    public List<User> getLatestUsersByFilter(@RequestBody(required = false) @Valid UserFilterRequest userFilter,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        return datingService.getLatestUsersByFilter(
                Optional.ofNullable(userFilter.toUserFilter()),
                WebCommons.PAGING_COUNT
        );
    }

    @RequestMapping(method = RequestMethod.POST, params = "pullUpDate")
    public List<User> getUsersByFilterFromDate(@RequestBody(required = false) @Valid UserFilterRequest userFilter,
                                               BindingResult bindingResult,
                                               @RequestParam("pullUpDate") LocalDateTime pullUpDate) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        return datingService.getUsersByFilterFromDate(
                Optional.ofNullable(userFilter.toUserFilter()),
                pullUpDate,
                WebCommons.PAGING_COUNT
        );
    }

    @RequestMapping(value = "/pull_up/", method = RequestMethod.POST)
    public ResponseEntity<String> pullUp() {
        datingService.pullCurrentUserUp();

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
