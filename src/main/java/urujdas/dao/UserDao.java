package urujdas.dao;

import urujdas.model.users.User;

public interface UserDao {

    User getById(Long id);

    User getByUsername(String username);

    User create(User user);

    void update(User user);
}
