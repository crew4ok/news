package urujdas.dao;

import urujdas.model.User;

public interface UserDao {

    User getById(Long id);

    User getByUsername(String username);

    User create(User user);
}
