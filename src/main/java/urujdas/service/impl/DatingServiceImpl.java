package urujdas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urujdas.dao.DatingDao;
import urujdas.dao.ImageDao;
import urujdas.dao.exception.NotFoundException;
import urujdas.model.images.Image;
import urujdas.model.users.User;
import urujdas.model.users.UserFilter;
import urujdas.service.DatingService;
import urujdas.service.UserService;
import urujdas.service.exception.PullUpTooFrequentException;
import urujdas.util.Validation;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class DatingServiceImpl implements DatingService {

    // minimum interval in minutes
    private static final int MINIMUM_PULL_UP_INTERVAL = 5;

    @Autowired
    private DatingDao datingDao;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageDao imageDao;

    @Override
    public List<User> getLatestUsersByFilter(Optional<UserFilter> filter, int count) {
        Validation.isNotNull(filter);
        Validation.isGreaterThanZero(count);

        UserFilter actualFilter = getUserFilter(filter);

        return constructUsers(datingDao.getLatestUsersByFilter(actualFilter, count));
    }

    @Override
    public List<User> getUsersByFilterFromDate(Optional<UserFilter> filter, LocalDateTime pullUpDate, int count) {
        Validation.isNotNull(filter);
        Validation.isNotNull(pullUpDate);
        Validation.isGreaterThanZero(count);

        UserFilter actualFilter = getUserFilter(filter);

        return constructUsers(datingDao.getUsersByFilterFromDate(actualFilter, pullUpDate, count));
    }

    private UserFilter getUserFilter(Optional<UserFilter> filter) {
        User currentUser = userService.getCurrentUser();

        if (filter.isPresent()) {
            UserFilter newFilter = filter.get();

            datingDao.updateUserFilter(currentUser, newFilter);

            return UserFilter.fromUserFilter(newFilter)
                    .withUserId(currentUser.getId())
                    .build();
        }

        UserFilter savedFilter = datingDao.findUserFilter(currentUser);

        if (savedFilter == null) {
            throw new NotFoundException(UserFilter.class, currentUser.getId());
        }
        return savedFilter;
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

    private List<User> constructUsers(List<User> users) {
        return users.stream()
                .map(this::constructUser)
                .collect(Collectors.toList());
    }

    private User constructUser(User user) {
        Optional<Image> image = imageDao.getByUser(user);

        if (image.isPresent()) {
            return User.fromUser(user)
                    .withImageId(image.get().getId())
                    .build();
        }

        return user;
    }

}
