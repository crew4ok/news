package ru.uruydas.social.dao;

import ru.uruydas.model.social.SocialNetworkType;
import ru.uruydas.model.users.User;

public interface SocialNetworkUserDao {

    User findByNetworkId(SocialNetworkType socialNetworkType, Long socialNetworkUserId);

}
