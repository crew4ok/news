package ru.uruydas.social.service;

import ru.uruydas.social.model.SocialNetworkType;
import ru.uruydas.users.model.User;

public interface SocialNetworkUserService {

    User findOrCreate(SocialNetworkType socialNetworkType, String accessToken);
}
