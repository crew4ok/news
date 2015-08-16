package ru.uruydas.social.dao;

import ru.uruydas.social.model.SocialNetworkType;
import ru.uruydas.users.model.User;

public interface SocialNetworkUserDao {

    User findByNetworkId(SocialNetworkType socialNetworkType, Long socialNetworkUserId);

}
