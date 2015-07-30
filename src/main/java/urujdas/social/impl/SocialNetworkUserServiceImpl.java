package urujdas.social.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import urujdas.dao.UserDao;
import urujdas.model.social.SocialNetworkType;
import urujdas.model.users.User;
import urujdas.social.dao.SocialNetworkUserDao;
import urujdas.social.model.SocialNetworkUser;
import urujdas.social.service.SocialNetworkApi;
import urujdas.social.service.SocialNetworkModelConverter;
import urujdas.social.service.SocialNetworkUserService;

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
