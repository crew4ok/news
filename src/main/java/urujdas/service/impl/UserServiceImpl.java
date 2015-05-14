package urujdas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urujdas.dao.UserDao;
import urujdas.model.User;
import urujdas.service.UserService;
import urujdas.service.exception.UserAlreadyExistsException;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getById(Long id) {
        return userDao.getById(id);
    }

    @Override
    public User getByUsername(String username) {
        return userDao.getByUsername(username);
    }

    @Override
    @Transactional(readOnly = false)
    public void register(User newUser) {
        User user = userDao.getByUsername(newUser.getUsername());
        if (user != null){
            throw new UserAlreadyExistsException();
        }

        userDao.create(newUser);
    }
}
