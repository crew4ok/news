package urujdas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urujdas.dao.DatingDao;
import urujdas.model.users.User;
import urujdas.model.users.UserFilter;
import urujdas.service.DatingService;
import urujdas.service.UserService;
import urujdas.service.exception.PullUpTooFrequentException;
import urujdas.util.Validation;

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

        return datingDao.getLatestUsersByFilter(actualFilter, count);
    }

    @Override
    public List<User> getUsersByFilterFromDate(UserFilter filter, LocalDateTime pullUpDate, int count) {
        Validation.isNotNull(filter);
        Validation.isNotNull(pullUpDate);
        Validation.isGreaterThanZero(count);

        UserFilter actualFilter = getUserFilter(filter);

        return datingDao.getUsersByFilterFromDate(actualFilter, pullUpDate, count);
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
