package ru.uruydas.social.service;

import ru.uruydas.model.social.SocialNetworkType;
import ru.uruydas.model.users.User;
import ru.uruydas.social.model.SocialNetworkUser;

public interface SocialNetworkModelConverter {

    User fromSocialNetworkUser(SocialNetworkType socialNetworkType, SocialNetworkUser user);

}
