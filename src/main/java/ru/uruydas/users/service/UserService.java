package ru.uruydas.users.service;

import ru.uruydas.users.model.User;

import java.util.List;

public interface UserService {

    User getCurrentUser();

    User getById(Long id);

    User getByUsername(String username);

    void register(User user);

    void update(User user);

    User attachImage(User user);

    List<User> attachImage(List<User> users);
}
