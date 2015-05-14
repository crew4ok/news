package urujdas.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import urujdas.model.User;
import urujdas.service.UserService;
import urujdas.service.exception.UserAlreadyExistsException;
import urujdas.web.common.WebCommons;
import urujdas.web.common.model.ErrorResponse;
import urujdas.web.common.model.ErrorType;
import urujdas.web.user.model.RegisterUserRequest;

import javax.validation.Valid;

@RestController
@RequestMapping(WebCommons.VERSION_PREFIX + "/register")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> register(@Valid @RequestBody RegisterUserRequest registerUserRequest) {
        userService.register(mapDTOToUser(registerUserRequest));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private static User mapDTOToUser(RegisterUserRequest dto) {
        return User.builder()
                .withUsername(dto.getUsername())
                .withPassword(dto.getPassword())
                .withFirstname(dto.getFirstname())
                .withLastname(dto.getLastname())
                .withBirthDate(dto.getBirthDate())
                .withEmail(dto.getEmail())
                .withGender(dto.getGender())
                .withPhone(dto.getPhone())
                .build();
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ErrorResponse exceptionHandler(UserAlreadyExistsException e) {
        return new ErrorResponse(ErrorType.USER_ALREADY_EXISTS, "User with the same username already exists");
    }
}
