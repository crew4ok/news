package urujdas.dao;

import org.jooq.exception.DataAccessException;
import org.testng.annotations.Test;
import urujdas.model.Gender;
import urujdas.model.User;

import static org.junit.Assert.assertNotNull;

public class UserDaoTest extends DaoBaseTest {

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
}
