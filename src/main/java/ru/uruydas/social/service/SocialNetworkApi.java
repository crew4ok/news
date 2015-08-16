package ru.uruydas.social.service;

import ru.uruydas.social.model.SocialNetworkType;
import ru.uruydas.social.model.SocialNetworkUser;

public interface SocialNetworkApi {

    SocialNetworkUser getUser(SocialNetworkType socialNetworkType, String accessToken);

}
