package urujdas.dao;

import org.jooq.exception.DataAccessException;
import org.testng.annotations.Test;
import urujdas.dao.exception.UpdateFailedException;
import urujdas.model.users.Gender;
import urujdas.model.users.GenderPreferences;
import urujdas.model.users.RelationsPreferences;
import urujdas.model.users.User;

import java.time.LocalDateTime;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

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

    @Test
    public void update_hp() throws Exception {
        User user = User.builder()
                .withUsername("username")
                .withPassword("password")
                .withFirstname("firstname")
                .withLastname("lastname")
                .withBirthDate(LocalDateTime.now())
                .withEmail("email")
                .withGender(Gender.MALE)
                .withPhone("phone")
                .withGenderPreferences(GenderPreferences.FEMALES)
                .withRelationsPreferences(RelationsPreferences.NON_SERIOUS_RELATIONS)
                .build();

        User createdUser = userDao.create(user);

        User update = User.builder()
                .withId(createdUser.getId())
                .withUsername(user.getUsername() + "1")
                .withPassword(user.getPassword() + "1")
                .withFirstname(user.getFirstname() + "1")
                .withLastname(user.getLastname() + "1")
                .withBirthDate(user.getBirthDate().minusYears(1))
                .withEmail(user.getEmail() + "1")
                .withGender(Gender.FEMALE)
                .withPhone(user.getPhone() + "1")
                .withGenderPreferences(GenderPreferences.MALES)
                .withRelationsPreferences(RelationsPreferences.SERIOUS_RELATIONS)
                .withPullUpDate(createdUser.getPullUpDate().minusYears(1))
                .build();

        userDao.update(update);

        User updatedUser = userDao.getById(createdUser.getId());

        assertNotEquals(updatedUser.getUsername(), update.getUsername());
        assertNotEquals(updatedUser.getPullUpDate(), update.getPullUpDate());

        assertEquals(updatedUser.getFirstname(), update.getFirstname());
        assertEquals(updatedUser.getLastname(), update.getLastname());
        assertEquals(updatedUser.getBirthDate(), update.getBirthDate());
        assertEquals(updatedUser.getEmail(), update.getEmail());
        assertEquals(updatedUser.getGender(), update.getGender());
        assertEquals(updatedUser.getPhone(), update.getPhone());
        assertEquals(updatedUser.getGenderPreferences(), update.getGenderPreferences());
        assertEquals(updatedUser.getRelationsPreferences(), update.getRelationsPreferences());
    }

    @Test
    public void update_nullAllTheFields() throws Exception {
        User user = User.builder()
                .withUsername("username")
                .withPassword("password")
                .withFirstname("firstname")
                .withLastname("lastname")
                .withBirthDate(LocalDateTime.now())
                .withEmail("email")
                .withGender(Gender.MALE)
                .withPhone("phone")
                .withGenderPreferences(GenderPreferences.FEMALES)
                .withRelationsPreferences(RelationsPreferences.NON_SERIOUS_RELATIONS)
                .build();

        User createdUser = userDao.create(user);

        User update = User.builder()
                .withId(createdUser.getId())
                .build();

        userDao.update(update);

        User updatedUser = userDao.getById(createdUser.getId());

        assertNotNull(updatedUser.getUsername());
        assertNotNull(updatedUser.getPullUpDate());

        assertNull(updatedUser.getFirstname(), update.getFirstname());
        assertNull(updatedUser.getLastname());
        assertNull(updatedUser.getBirthDate());
        assertNull(updatedUser.getEmail());
        assertNull(updatedUser.getGender());
        assertNull(updatedUser.getPhone());
        assertNull(updatedUser.getGenderPreferences());
        assertNull(updatedUser.getRelationsPreferences());
    }

    @Test(expectedExceptions = UpdateFailedException.class)
    public void update_noSuchUser() throws Exception {
        userDao.update(User.builder().build());
    }
}
