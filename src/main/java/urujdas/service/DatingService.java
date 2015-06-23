package urujdas.service;

import urujdas.model.users.User;
import urujdas.model.users.UserFilter;

import java.time.LocalDateTime;
import java.util.List;

public interface DatingService {

    List<User> getLatestUsersByFilter(UserFilter filter, int count);

    List<User> getUsersByFilterFromDate(UserFilter filter, LocalDateTime pullUpDate, int count);

    UserFilter findCurrentUserFilter();

    void pullCurrentUserUp();

}
