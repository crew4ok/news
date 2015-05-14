package urujdas.service;

import urujdas.model.User;

public interface UserService {

    User getById(Long id);

    User getByUsername(String username);

    void register(User user);
}
