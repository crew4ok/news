package urujdas.service;

import urujdas.model.users.User;
import urujdas.model.users.UserFilter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DatingService {

    List<User> getLatestUsersByFilter(Optional<UserFilter> filter, int count);

    List<User> getUsersByFilterFromDate(Optional<UserFilter> filter, LocalDateTime pullUpDate, int count);

    UserFilter findCurrentUserFilter();

    void pullCurrentUserUp();

}
