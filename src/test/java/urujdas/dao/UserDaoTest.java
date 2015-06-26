package urujdas.dao;

import org.jooq.exception.DataAccessException;
import org.testng.annotations.Test;
import urujdas.dao.exception.NotFoundException;
import urujdas.model.users.Gender;
import urujdas.model.users.User;

import static org.junit.Assert.assertEquals;
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

    @Test(expectedExceptions = NotFoundException.class)
    public void getByUser_notFound() throws Exception {
        userDao.getByUsername("username");
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

    @Test
    public void getById_hp() throws Exception {
        User user = createDefaultUser();
        User actualUser = userDao.getById(user.getId());

        assertEquals(actualUser, user);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void getById_notFound() throws Exception {
        userDao.getById(1L);
    }
}
