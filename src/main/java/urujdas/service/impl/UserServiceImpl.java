package urujdas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return this.getByUsername(username);
    }

    @Override
    public User getById(Long id) {
        return userDao.getById(id);
    }

    @Override
    public User getByUsername(String username) {
        if (username == null) {
            return null;
        }
        return userDao.getByUsername(username);
    }

    @Override
    @Transactional(readOnly = false)
    public void register(User newUser) {
        if (userDao.getByUsername(newUser.getUsername()) != null){
            throw new UserAlreadyExistsException();
        }

        User userWithEncodedPassword = User.fromUser(newUser)
                .withPassword(passwordEncoder.encode(newUser.getPassword()))
                .build();

        userDao.create(userWithEncodedPassword);
    }
}
