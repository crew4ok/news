package urujdas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urujdas.dao.ImageDao;
import urujdas.dao.UserDao;
import urujdas.model.images.Image;
import urujdas.model.users.User;
import urujdas.service.UserService;
import urujdas.service.exception.UserAlreadyExistsException;
import urujdas.util.Validation;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ImageDao imageDao;

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return this.getByUsername(username);
    }

    @Override
    public User getById(Long id) {
        Validation.isGreaterThanZero(id);

        return constructUser(userDao.getById(id));
    }

    @Override
    public User getByUsername(String username) {
        Validation.isNotNull(username);

        return constructUser(userDao.getByUsername(username));
    }

    @Override
    @Transactional(readOnly = false)
    public void register(User newUser) {
        if (userDao.checkExists(newUser.getUsername())){
            throw new UserAlreadyExistsException();
        }

        User userWithEncodedPassword = User.fromUser(newUser)
                .withPassword(passwordEncoder.encode(newUser.getPassword()))
                .build();

        User createdUser = userDao.create(userWithEncodedPassword);

        if (newUser.getImageId() != null) {
            Image image = imageDao.getById(newUser.getImageId());
            imageDao.linkToUser(image, createdUser);
        }
    }

    private User constructUser(User user) {
        Optional<Image> image = imageDao.getByUser(user);

        if (image.isPresent()) {
            return User.fromUser(user)
                    .withImageId(image.get().getId())
                    .build();
        }

        return user;
    }
}
