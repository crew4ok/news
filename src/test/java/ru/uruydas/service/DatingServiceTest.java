package ru.uruydas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.uruydas.dao.DatingDao;
import ru.uruydas.model.users.User;
import ru.uruydas.model.users.UserFilter;
import ru.uruydas.service.exception.PullUpTooFrequentException;
import ru.uruydas.util.exception.InvalidParamException;
import ru.uruydas.service.impl.DatingServiceImpl;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

@ContextConfiguration(classes = DatingServiceTest.LocalContext.class)
public class DatingServiceTest extends BaseServiceTest {
    @Autowired
    private DatingService datingService;

    @Autowired
    private DatingDao datingDao;

    @Autowired
    private UserService userService;

    @AfterMethod
    public void resetMocks() {
        verifyNoMoreInteractions(datingDao, userService);
        reset(datingDao, userService);
    }

    @Test(expectedExceptions = InvalidParamException.class)
    public void getLatestUsersByFilter_nullFilter() throws Exception {
        datingService.getLatestUsersByFilter(null, 1);
    }

    @Test(expectedExceptions = InvalidParamException.class)
    public void getLatestUsersByFilter_countIsNegative() throws Exception {
        datingService.getLatestUsersByFilter(mock(UserFilter.class), -1);
    }

    @Test(expectedExceptions = InvalidParamException.class)
    public void getLatestUsersByFilter_countIsZero() throws Exception {
        datingService.getLatestUsersByFilter(mock(UserFilter.class), 0);
    }

    @Test
    public void getLatestUsersByFilter_hp() throws Exception {
        UserFilter filter = UserFilter.builder().build();
        int count = 1;

        long userId = 1;
        User currentUser = mock(User.class);
        when(currentUser.getId()).thenReturn(userId);
        when(userService.getCurrentUser()).thenReturn(currentUser);

        List<User> expectedUsers = Collections.singletonList(mock(User.class));
        when(datingDao.getLatestUsersByFilter(any(UserFilter.class), eq(count))).thenReturn(expectedUsers);
        when(userService.attachImage(expectedUsers)).thenReturn(expectedUsers);

        List<User> actualUsers = datingService.getLatestUsersByFilter(filter, count);

        assertEquals(actualUsers, expectedUsers);

        verify(userService).getCurrentUser();
        verify(userService).attachImage(expectedUsers);
        verify(datingDao).updateUserFilter(currentUser, filter);
        verify(datingDao).getLatestUsersByFilter(any(UserFilter.class), eq(count));
    }

    @Test(expectedExceptions = InvalidParamException.class)
    public void getUsersByFilterFromDate_nullFilter() throws Exception {
        datingService.getUsersByFilterFromDate(null, LocalDateTime.now(), 1);
    }

    @Test(expectedExceptions = InvalidParamException.class)
    public void getUsersByFilterFromDate_nullDate() throws Exception {
        datingService.getUsersByFilterFromDate(mock(UserFilter.class), null, 1);
    }

    @Test(expectedExceptions = InvalidParamException.class)
    public void getUsersByFilterFromDate_countIsNegative() throws Exception {
        datingService.getUsersByFilterFromDate(mock(UserFilter.class), LocalDateTime.now(), -1);
    }

    @Test(expectedExceptions = InvalidParamException.class)
    public void getUsersByFilterFromDate_countIsZero() throws Exception {
        datingService.getUsersByFilterFromDate(mock(UserFilter.class), LocalDateTime.now(), 0);
    }

    @Test
    public void getUsersByFilterFromDate_hp() throws Exception {
        UserFilter filter = UserFilter.builder().build();
        int count = 1;

        User currentUser = mock(User.class);
        when(userService.getCurrentUser()).thenReturn(currentUser);

        LocalDateTime pullUpDate = LocalDateTime.now();
        List<User> expectedUsers = Collections.singletonList(mock(User.class));
        when(datingDao.getUsersByFilterFromDate(any(UserFilter.class), eq(pullUpDate), eq(count)))
                .thenReturn(expectedUsers);
        when(userService.attachImage(expectedUsers)).thenReturn(expectedUsers);

        List<User> actualUsers = datingService.getUsersByFilterFromDate(filter, pullUpDate, count);

        assertEquals(actualUsers, expectedUsers);

        verify(userService).getCurrentUser();
        verify(userService).attachImage(expectedUsers);
        verify(datingDao).updateUserFilter(currentUser, filter);
        verify(datingDao).getUsersByFilterFromDate(any(UserFilter.class), eq(pullUpDate), eq(count));
    }

    @Test
    public void findCurrentUsersFilter_hp() throws Exception {
        User user = mock(User.class);
        when(userService.getCurrentUser()).thenReturn(user);

        UserFilter expectedFilter = UserFilter.builder().build();
        when(datingDao.findUserFilter(user)).thenReturn(expectedFilter);

        UserFilter actualFilter = datingService.findCurrentUserFilter();

        assertEquals(actualFilter, expectedFilter);

        verify(userService).getCurrentUser();
        verify(datingDao).findUserFilter(user);
    }

    @Test
    public void pullCurrentUserUp_hp() throws Exception {
        User user = mock(User.class);
        when(user.getPullUpDate()).thenReturn(LocalDateTime.now().minusDays(1));
        when(userService.getCurrentUser()).thenReturn(user);

        datingService.pullCurrentUserUp();

        verify(userService).getCurrentUser();
        verify(datingDao).pullUserUp(user);
    }

    @Test(expectedExceptions = PullUpTooFrequentException.class)
    public void pullCurrentUserUp_minimumIntervalHasNotBeenReached() throws Exception {
        User user = mock(User.class);
        when(user.getPullUpDate()).thenReturn(LocalDateTime.now(Clock.systemUTC()));
        when(userService.getCurrentUser()).thenReturn(user);

        try {
            datingService.pullCurrentUserUp();
        } finally {
            verify(userService).getCurrentUser();
        }
    }

    @Configuration
    static class LocalContext {

        @Bean
        public DatingService datingService() {
            return new DatingServiceImpl();
        }

        @Bean
        public DatingDao datingDao() {
            return mock(DatingDao.class);
        }

        @Bean
        public UserService userService() {
            return mock(UserService.class);
        }
    }
}
