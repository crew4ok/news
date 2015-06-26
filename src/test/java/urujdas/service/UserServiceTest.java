package urujdas.service;

import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import urujdas.dao.ImageDao;
import urujdas.dao.UserDao;
import urujdas.model.users.Gender;
import urujdas.model.users.GenderPreferences;
import urujdas.model.users.RelationsPreferences;
import urujdas.model.users.User;
import urujdas.service.exception.UserAlreadyExistsException;
import urujdas.service.impl.UserServiceImpl;

import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@ContextConfiguration(classes = UserServiceTest.LocalContext.class)
public class UserServiceTest extends BaseServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @AfterMethod
    public void resetMocks() throws Exception {
        verifyNoMoreInteractions(userDao);
        reset(userDao);
    }

    @Test(expectedExceptions = UserAlreadyExistsException.class)
    public void register_userAlreadyExists() throws Exception {
        User user = User.builder()
                .withUsername("user")
                .withPassword("pass")
                .build();

        when(userDao.checkExists(user.getUsername())).thenReturn(true);

        try {
            userService.register(user);
            userService.register(user);
        } finally {
            verify(userDao).checkExists(user.getUsername());
        }
    }

    @Test
    public void register_hp() {
        User expectedUser = User.builder()
                .withUsername("user")
                .withPassword("pass")
                .withFirstname("firstname")
                .withLastname("lastname")
                .withBirthDate(LocalDateTime.now())
                .withEmail("email")
                .withGender(Gender.MALE)
                .withPhone("phone")
                .withPullUpDate(LocalDateTime.now())
                .withGenderPreferences(GenderPreferences.MALES)
                .withRelationsPreferences(RelationsPreferences.INTERESTS_RELATIONS)
                .withImageId(1L)
                .build();

        when(userDao.checkExists(expectedUser.getUsername())).thenReturn(false);

        userService.register(expectedUser);

        verify(userDao).checkExists(expectedUser.getUsername());
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userDao).create(argumentCaptor.capture());

        User actualUser = argumentCaptor.getValue();

        assertNotNull(actualUser);
        assertNotNull(actualUser.getPassword());
        assertEquals(actualUser.getUsername(), expectedUser.getUsername());
        assertEquals(actualUser.getFirstname(), expectedUser.getFirstname());
        assertEquals(actualUser.getLastname(), expectedUser.getLastname());
        assertEquals(actualUser.getBirthDate(), expectedUser.getBirthDate());
        assertEquals(actualUser.getEmail(), expectedUser.getEmail());
        assertEquals(actualUser.getGender(), expectedUser.getGender());
        assertEquals(actualUser.getPhone(), expectedUser.getPhone());
        assertEquals(actualUser.getPullUpDate(), expectedUser.getPullUpDate());
        assertEquals(actualUser.getGenderPreferences(), expectedUser.getGenderPreferences());
        assertEquals(actualUser.getRelationsPreferences(), expectedUser.getRelationsPreferences());
    }

    @Configuration
    static class LocalContext {
        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }

        @Bean
        public UserDao userDao() {
            return mock(UserDao.class);
        }

        @Bean
        public ImageDao imageDao() {
            return mock(ImageDao.class);
        }
    }
}
