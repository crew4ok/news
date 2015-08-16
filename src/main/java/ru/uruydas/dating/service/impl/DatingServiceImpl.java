package ru.uruydas.dating.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.uruydas.common.service.exception.PullUpTooFrequentException;
import ru.uruydas.common.util.Validation;
import ru.uruydas.dating.dao.DatingDao;
import ru.uruydas.dating.service.DatingService;
import ru.uruydas.users.model.User;
import ru.uruydas.users.model.UserFilter;
import ru.uruydas.users.service.UserService;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class DatingServiceImpl implements DatingService {

    // minimum interval in minutes
    private static final int MINIMUM_PULL_UP_INTERVAL = 5;

    @Autowired
    private DatingDao datingDao;

    @Autowired
    private UserService userService;

    @Override
    public List<User> getLatestUsersByFilter(UserFilter filter, int count) {
        Validation.isNotNull(filter);
        Validation.isGreaterThanZero(count);

        UserFilter actualFilter = getUserFilter(filter);

        List<User> users = datingDao.getLatestUsersByFilter(actualFilter, count);
        return userService.attachImage(users);
    }

    @Override
    public List<User> getUsersByFilterFromDate(UserFilter filter, LocalDateTime pullUpDate, int count) {
        Validation.isNotNull(filter);
        Validation.isNotNull(pullUpDate);
        Validation.isGreaterThanZero(count);

        UserFilter actualFilter = getUserFilter(filter);

        List<User> users = datingDao.getUsersByFilterFromDate(actualFilter, pullUpDate, count);
        return userService.attachImage(users);
    }

    private UserFilter getUserFilter(UserFilter filter) {
        User currentUser = userService.getCurrentUser();

        datingDao.updateUserFilter(currentUser, filter);

        return UserFilter.fromUserFilter(filter)
                .withUserId(currentUser.getId())
                .build();
    }

    @Override
    @Transactional(readOnly = false)
    public UserFilter findCurrentUserFilter() {
        User currentUser = userService.getCurrentUser();

        return datingDao.findUserFilter(currentUser);
    }

    @Override
    @Transactional(readOnly = false)
    public void pullCurrentUserUp() {
        User currentUser = userService.getCurrentUser();
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());

        if (currentUser.getPullUpDate().plusMinutes(MINIMUM_PULL_UP_INTERVAL).isAfter(now)) {
            throw new PullUpTooFrequentException(currentUser.getPullUpDate());
        }

        datingDao.pullUserUp(currentUser);
    }
}
