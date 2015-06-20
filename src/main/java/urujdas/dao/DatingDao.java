package urujdas.dao;

import urujdas.model.users.User;
import urujdas.model.users.UserFilter;

import java.time.LocalDateTime;
import java.util.List;

public interface DatingDao {

    List<User> getLatestUsersByFilter(UserFilter filter, int count);

    List<User> getUsersByFilterFromDate(UserFilter filter, LocalDateTime pullUpDate, int count);

    UserFilter findUserFilter(User user);

    void updateUserFilter(User user, UserFilter userFilter);

    void pullUserUp(User user);
}
