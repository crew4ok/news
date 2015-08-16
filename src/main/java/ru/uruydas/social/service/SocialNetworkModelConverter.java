package ru.uruydas.social.service;

import ru.uruydas.social.model.SocialNetworkType;
import ru.uruydas.social.model.SocialNetworkUser;
import ru.uruydas.users.model.User;

public interface SocialNetworkModelConverter {

    User fromSocialNetworkUser(SocialNetworkType socialNetworkType, SocialNetworkUser user);

}
