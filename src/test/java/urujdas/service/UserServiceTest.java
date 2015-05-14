package urujdas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;
import urujdas.config.DaoConfig;
import urujdas.config.ServiceConfig;
import urujdas.model.Gender;
import urujdas.model.User;
import urujdas.service.exception.UserAlreadyExistsException;

import java.time.LocalDateTime;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@ContextConfiguration(classes = {DaoConfig.class, ServiceConfig.class})
public class UserServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private UserService userService;

    @Test(expectedExceptions = UserAlreadyExistsException.class)
    public void register_userAlreadyExists() throws Exception {
        User user = User.builder()
                .withUsername("user")
                .withPassword("pass")
                .build();

        userService.register(user);
        userService.register(user);
    }

    @Test
    public void register_hp() {
        User expectedUser = User.builder()
                .withUsername("expectedUser")
                .withPassword("pass")
                .withFirstname("firstname")
                .withLastname("lastname")
                .withBirthDate(LocalDateTime.now())
                .withEmail("email")
                .withGender(Gender.MALE)
                .withPhone("phone")
                .build();

        userService.register(expectedUser);

        User actualUser = userService.getByUsername(expectedUser.getUsername());

        assertNotNull(actualUser);
        assertEquals(actualUser.getUsername(), expectedUser.getUsername());
        assertEquals(actualUser.getFirstname(), expectedUser.getFirstname());
        assertEquals(actualUser.getLastname(), expectedUser.getLastname());
        assertEquals(actualUser.getBirthDate(), expectedUser.getBirthDate());
        assertEquals(actualUser.getEmail(), expectedUser.getEmail());
        assertEquals(actualUser.getGender(), expectedUser.getGender());
        assertEquals(actualUser.getPhone(), expectedUser.getPhone());
    }
}
