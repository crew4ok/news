package ru.uruydas.dao;

import org.testng.annotations.Test;
import ru.uruydas.users.model.AgeRange;
import ru.uruydas.users.model.Gender;
import ru.uruydas.users.model.GenderPreferences;
import ru.uruydas.users.model.RelationsPreferences;
import ru.uruydas.users.model.User;
import ru.uruydas.users.model.UserFilter;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

public class DatingDaoTest extends DaoBaseTest {

    @Test
    public void getLatestUsersByFilter_gender() throws Exception {
        User currentUser = createDefaultUser();

        UserFilter userFilter = UserFilter.builder()
                .withUserId(currentUser.getId())
                .withGender(Gender.MALE)
                .build();

        int totalCount = 10;
        int latestCount = 2;

        Function<Integer, User> userSupplier = (i) -> {
            Gender gender = i % 2 == 0 ? Gender.MALE : Gender.FEMALE;
            return createDefaultUser(
                    gender,
                    GenderPreferences.MALES,
                    RelationsPreferences.INTERESTS_RELATIONS,
                    LocalDateTime.now()
            );
        };

        testGetLatestUsersByFilter(userFilter, userSupplier, totalCount, latestCount);
    }

    @Test
    public void getLatestUsersByFilter_genderPreferences() throws Exception {
        User currentUser = createDefaultUser();

        UserFilter userFilter = UserFilter.builder()
                .withUserId(currentUser.getId())
                .withGenderPreferences(GenderPreferences.FEMALES)
                .build();

        int totalCount = 10;
        int latestCount = 2;

        Function<Integer, User> userSupplier =  (i) -> {
            GenderPreferences genderPreferences = i % 2 == 0 ? GenderPreferences.FEMALES : GenderPreferences.MALES;
            return createDefaultUser(
                    Gender.MALE,
                    genderPreferences,
                    RelationsPreferences.INTERESTS_RELATIONS,
                    LocalDateTime.now()
            );
        };

        testGetLatestUsersByFilter(userFilter, userSupplier, totalCount, latestCount);
    }

    @Test
    public void getLatestUsersByFilter_relationsPreferences() throws Exception {
        User currentUser = createDefaultUser();

        UserFilter userFilter = UserFilter.builder()
                .withUserId(currentUser.getId())
                .withRelationsPreferences(RelationsPreferences.SERIOUS_RELATIONS)
                .build();

        int totalCount = 10;
        int latestCount = 2;

        Function<Integer, User> userSupplier =  (i) -> {
            RelationsPreferences relationPreferences = i % 2 == 0
                    ? RelationsPreferences.SERIOUS_RELATIONS
                    : RelationsPreferences.INTERESTS_RELATIONS;
            return createDefaultUser(
                    Gender.MALE,
                    GenderPreferences.MALES,
                    relationPreferences,
                    LocalDateTime.now()
            );
        };

        testGetLatestUsersByFilter(userFilter, userSupplier, totalCount, latestCount);
    }

    @Test
    public void getLatestUsersByFilter_ageRangeLower() throws Exception {
        User currentUser = createDefaultUser();

        int lower = 20;

        UserFilter userFilter = UserFilter.builder()
                .withUserId(currentUser.getId())
                .withAgeRange(new AgeRange(lower, null))
                .build();

        int totalCount = 10;
        int latestCount = 2;

        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
        LocalDateTime baseDateTime = now.minusYears(lower + 1);

        System.out.println(baseDateTime);

        Function<Integer, User> userSupplier =  (i) -> {
            LocalDateTime birthDate = i % 2 == 0
                    ? baseDateTime.minusYears(i)
                    : baseDateTime.plusYears(10);

            return createDefaultUser(
                    Gender.MALE,
                    GenderPreferences.MALES_AND_FEMALES,
                    RelationsPreferences.INTERESTS_RELATIONS,
                    birthDate
            );
        };

        testGetLatestUsersByFilter(userFilter, userSupplier, totalCount, latestCount);
    }

    @Test
    public void getLatestUsersByFilter_ageRangeHigher() throws Exception {
        User currentUser = createDefaultUser();

        int higher = 25;

        UserFilter userFilter = UserFilter.builder()
                .withUserId(currentUser.getId())
                .withAgeRange(new AgeRange(null, higher))
                .build();

        int totalCount = 10;
        int latestCount = 2;

        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
        LocalDateTime baseDateTime = now.minusYears(higher + 1);

        System.out.println(baseDateTime);

        Function<Integer, User> userSupplier =  (i) -> {
            LocalDateTime birthDate = i % 2 == 0
                    ? baseDateTime.plusYears(i)
                    : baseDateTime.minusYears(10);

            return createDefaultUser(
                    Gender.MALE,
                    GenderPreferences.MALES_AND_FEMALES,
                    RelationsPreferences.INTERESTS_RELATIONS,
                    birthDate
            );
        };

        testGetLatestUsersByFilter(userFilter, userSupplier, totalCount, latestCount);
    }

    @Test
    public void getLatestUsersByFilter_ageRangeLowerAndHigher() throws Exception {
        User currentUser = createDefaultUser();

        int lower = 20;
        int higher = 25;

        UserFilter userFilter = UserFilter.builder()
                .withUserId(currentUser.getId())
                .withAgeRange(new AgeRange(lower, higher))
                .build();

        int totalCount = 10;
        int latestCount = 2;

        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
        LocalDateTime baseDateTime = now.minusYears(higher);

        Function<Integer, User> userSupplier =  (i) -> {
            LocalDateTime birthDate = i % 2 == 0
                    ? baseDateTime.plusYears(i % (higher - lower))
                    : baseDateTime.minusYears(50);

            return createDefaultUser(
                    Gender.MALE,
                    GenderPreferences.MALES_AND_FEMALES,
                    RelationsPreferences.INTERESTS_RELATIONS,
                    birthDate
            );
        };

        testGetLatestUsersByFilter(userFilter, userSupplier, totalCount, latestCount);
    }

    private void testGetLatestUsersByFilter(UserFilter filter, Function<Integer, User> userSupplier,
                                            int totalCount, int latestCount) {
        List<User> expectedUsers = new ArrayList<>();
        for (int i = 0; i < totalCount; i++) {
            User user = userSupplier.apply(i);
            if (i % 2 == 0) {
                expectedUsers.add(user);
            }
        }
        expectedUsers = expectedUsers.subList(expectedUsers.size() - latestCount, expectedUsers.size());
        Collections.reverse(expectedUsers);

        List<User> actualUsers = datingDao.getLatestUsersByFilter(filter, latestCount);
        assertEquals(actualUsers, expectedUsers);
    }

    @Test(enabled = false)
    public void getUsersByFilterFromDate_hp() throws Exception {
        User currentUser = createDefaultUser();

        UserFilter filter = UserFilter.builder()
                .withUserId(currentUser.getId())
                .withGender(Gender.MALE)
                .build();

        int totalCount = 10;
        int latestCount = 2;

        Function<Integer, User> userSupplier = (i) -> {
            Gender gender = i % 2 == 0 ? Gender.MALE : Gender.FEMALE;
            return createDefaultUser(
                    gender,
                    GenderPreferences.MALES,
                    RelationsPreferences.INTERESTS_RELATIONS,
                    LocalDateTime.now()
            );
        };

        List<User> expectedUsers = new ArrayList<>();
        for (int i = 0; i < totalCount; i++) {
            User user = userSupplier.apply(i);
            if (i % 2 == 0) {
                expectedUsers.add(user);
            }
        }
        expectedUsers = expectedUsers.subList(expectedUsers.size() - latestCount, expectedUsers.size());
        Collections.reverse(expectedUsers);

        List<User> actualUsers = datingDao.getUsersByFilterFromDate(
                filter,
                LocalDateTime.now().minusMinutes(1),
                latestCount
        );
        assertEquals(actualUsers, expectedUsers);
    }

    @Test
    public void findUserFilter_noFilter() throws Exception {
        User user = createDefaultUser();

        UserFilter userFilter = datingDao.findUserFilter(user);

        assertNull(userFilter);
    }

    @Test
    public void updateUserFilter_saveEmptyFilter() throws Exception {
        User user = createDefaultUser();

        UserFilter expectedFilter = UserFilter.builder().build();

        datingDao.updateUserFilter(user, expectedFilter);

        UserFilter actualFilter = datingDao.findUserFilter(user);

        assertEquals(actualFilter.getUserId(), user.getId());
        assertEquals(actualFilter.getGender(), expectedFilter.getGender());
        assertEquals(actualFilter.getGenderPreferences(), expectedFilter.getGenderPreferences());
        assertEquals(actualFilter.getRelationsPreferences(), expectedFilter.getRelationsPreferences());
        assertEquals(actualFilter.getAgeRange(), expectedFilter.getAgeRange());
    }

    @Test
    public void updateUserFilter_noSavedFilter() throws Exception {
        User user = createDefaultUser();

        UserFilter expectedFilter = UserFilter.builder()
                .withGender(Gender.MALE)
                .withGenderPreferences(GenderPreferences.MALES_AND_FEMALES)
                .withRelationsPreferences(RelationsPreferences.NON_SERIOUS_RELATIONS)
                .withAgeRange(new AgeRange(1, 2))
                .build();

        datingDao.updateUserFilter(user, expectedFilter);

        UserFilter actualFilter = datingDao.findUserFilter(user);

        assertEquals(actualFilter.getUserId(), user.getId());
        assertEquals(actualFilter.getGender(), expectedFilter.getGender());
        assertEquals(actualFilter.getGenderPreferences(), expectedFilter.getGenderPreferences());
        assertEquals(actualFilter.getRelationsPreferences(), expectedFilter.getRelationsPreferences());
        assertEquals(actualFilter.getAgeRange(), expectedFilter.getAgeRange());
    }

    @Test
    public void updateUserFilter_filterAlreadySaved() throws Exception {
        User user = createDefaultUser();

        UserFilter oldFilter = UserFilter.builder()
                .withGender(Gender.MALE)
                .withGenderPreferences(GenderPreferences.MALES_AND_FEMALES)
                .withRelationsPreferences(RelationsPreferences.NON_SERIOUS_RELATIONS)
                .withAgeRange(new AgeRange(1, 2))
                .build();

        datingDao.updateUserFilter(user, oldFilter);

        UserFilter newFilter = UserFilter.builder()
                .withUserId(user.getId() + 1)
                .withGender(Gender.FEMALE)
                .withGenderPreferences(GenderPreferences.FEMALES)
                .withRelationsPreferences(RelationsPreferences.INTERESTS_RELATIONS)
                .withAgeRange(new AgeRange(2, 3))
                .build();

        datingDao.updateUserFilter(user, newFilter);

        UserFilter actualFilter = datingDao.findUserFilter(user);

        assertEquals(actualFilter.getUserId(), user.getId());
        assertEquals(actualFilter.getGender(), newFilter.getGender());
        assertEquals(actualFilter.getGenderPreferences(), newFilter.getGenderPreferences());
        assertEquals(actualFilter.getRelationsPreferences(), newFilter.getRelationsPreferences());
        assertEquals(actualFilter.getAgeRange(), newFilter.getAgeRange());
    }

    @Test
    public void pullUserUp_hp() throws Exception {
        User user = createDefaultUser();

        assertNotNull(user.getPullUpDate());

        datingDao.pullUserUp(user);

        user = userDao.getById(user.getId());

        // we cannot check if the date has changed, since tests are executing
        // in one transaction, and postgresql returns the same current_timestamp in
        // one transaction
        assertNotNull(user.getPullUpDate());
    }
}
