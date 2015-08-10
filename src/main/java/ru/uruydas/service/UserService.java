package ru.uruydas.service;

import ru.uruydas.model.users.User;

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
