package urujdas.dao;

import urujdas.model.users.User;

public interface UserDao {

    boolean checkExists(String username);

    User getById(Long id);

    User getByUsername(String username);

    User create(User user);
}
