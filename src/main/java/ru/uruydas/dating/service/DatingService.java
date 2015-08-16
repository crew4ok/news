package ru.uruydas.dating.service;

import ru.uruydas.users.model.User;
import ru.uruydas.users.model.UserFilter;

import java.time.LocalDateTime;
import java.util.List;

public interface DatingService {

    List<User> getLatestUsersByFilter(UserFilter filter, int count);

    List<User> getUsersByFilterFromDate(UserFilter filter, LocalDateTime pullUpDate, int count);

    UserFilter findCurrentUserFilter();

    void pullCurrentUserUp();

}
