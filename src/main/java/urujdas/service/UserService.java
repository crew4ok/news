package urujdas.service;

import urujdas.model.users.User;

public interface UserService {

    User getCurrentUser();

    User getById(Long id);

    User getByUsername(String username);

    void register(User user);
}
