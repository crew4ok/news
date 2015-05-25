package urujdas.service;

import urujdas.model.User;

public interface UserService {

    User getCurrentUser();

    User getById(Long id);

    User getByUsername(String username);

    void register(User user);
}
