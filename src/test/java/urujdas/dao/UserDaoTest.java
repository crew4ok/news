package urujdas.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;
import urujdas.config.DaoConfig;
import urujdas.model.Gender;
import urujdas.model.User;

import static org.junit.Assert.assertNotNull;

@ContextConfiguration(classes = DaoConfig.class)
public class UserDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private UserDao userDao;

    @Test
    public void getById() {
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
}
