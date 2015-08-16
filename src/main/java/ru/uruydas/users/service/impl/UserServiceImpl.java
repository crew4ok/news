package ru.uruydas.users.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.uruydas.common.service.exception.UserAlreadyExistsException;
import ru.uruydas.common.util.Validation;
import ru.uruydas.images.dao.ImageDao;
import ru.uruydas.images.model.Image;
import ru.uruydas.social.security.SocialNetworkUserAuthentication;
import ru.uruydas.users.dao.UserDao;
import ru.uruydas.users.model.User;
import ru.uruydas.users.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.uruydas.common.util.MapperUtils.fromNullable;

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

        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();

            return userDao.getByUsername(username);
         } else if (authentication instanceof SocialNetworkUserAuthentication) {
            Long userId = (Long) authentication.getPrincipal();

            return userDao.getById(userId);
        }

        throw new RuntimeException("Unknown authentication class: " + authentication.getClass());
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

    @Override
    @Transactional(readOnly = false)
    public void update(User user) {
        User currentUser = getCurrentUser();

        user = User.fromUser(user)
                .withId(currentUser.getId())
                .withPassword(fromNullable(user.getPassword(), passwordEncoder::encode))
                .build();

        userDao.update(user);

        if (user.getImageId() != null) {
            Image image = imageDao.getById(user.getImageId());
            imageDao.linkToUser(image, user);
        }
    }

    @Override
    public User attachImage(User user) {
        return constructUser(user);
    }

    @Override
    public List<User> attachImage(List<User> users) {
        return users.stream()
                .map(this::constructUser)
                .collect(Collectors.toList());
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
