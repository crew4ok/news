package ru.uruydas.dating.dao;

import ru.uruydas.users.model.User;
import ru.uruydas.users.model.UserFilter;

import java.time.LocalDateTime;
import java.util.List;

public interface DatingDao {

    List<User> getLatestUsersByFilter(UserFilter filter, int count);

    List<User> getUsersByFilterFromDate(UserFilter filter, LocalDateTime pullUpDate, int count);

    UserFilter findUserFilter(User user);

    void updateUserFilter(User user, UserFilter userFilter);

    void pullUserUp(User user);
}
