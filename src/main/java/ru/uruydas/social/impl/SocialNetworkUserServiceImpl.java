package ru.uruydas.social.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.uruydas.social.dao.SocialNetworkUserDao;
import ru.uruydas.social.model.SocialNetworkType;
import ru.uruydas.social.model.SocialNetworkUser;
import ru.uruydas.social.service.SocialNetworkApi;
import ru.uruydas.social.service.SocialNetworkModelConverter;
import ru.uruydas.social.service.SocialNetworkUserService;
import ru.uruydas.users.dao.UserDao;
import ru.uruydas.users.model.User;

@Service
public class SocialNetworkUserServiceImpl implements SocialNetworkUserService {

    @Autowired
    private SocialNetworkUserDao socialNetworkUserDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SocialNetworkApi socialNetworkApi;

    @Autowired
    private SocialNetworkModelConverter socialNetworkModelConverter;

    @Override
    public User findOrCreate(SocialNetworkType socialNetworkType, String accessToken) {
        SocialNetworkUser socialNetworkUser = socialNetworkApi.getUser(socialNetworkType, accessToken);

        User user = socialNetworkUserDao.findByNetworkId(socialNetworkType, socialNetworkUser.getId());

        if (user != null) {
            return user;
        }

        User userToCreate = socialNetworkModelConverter.fromSocialNetworkUser(socialNetworkType, socialNetworkUser);

        return userDao.create(userToCreate);
    }
}
