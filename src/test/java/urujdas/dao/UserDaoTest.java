package urujdas.dao;

import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;
import urujdas.config.DaoConfig;
import urujdas.dao.impl.UserDaoImpl;
import urujdas.model.Gender;
import urujdas.model.User;

import static org.junit.Assert.assertNotNull;

@ContextConfiguration(classes = {
        DaoConfig.class,
        UserDaoTest.LocalContext.class
})
public class UserDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private UserDao userDao;

    @Test
    public void getByUsername_hp() {
        userDao.create(
                User.builder()
                        .withUsername("crew4ok")
                        .withPassword("asfd")
                        .withGender(Gender.MALE)
                        .build()
        );

        User user = userDao.getByUsername("crew4ok");

        assertNotNull(user);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void create_usernameUniqueness() throws Exception {
        User user = User.builder()
                .withUsername("username")
                .withPassword("password")
                .build();
        userDao.create(user);
        userDao.create(user);
    }

    @Configuration
    static class LocalContext {
        @Bean
        public UserDao userDao() {
            return new UserDaoImpl();
        }
    }

}
