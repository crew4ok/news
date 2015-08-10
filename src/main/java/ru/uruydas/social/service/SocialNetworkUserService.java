package ru.uruydas.social.service;

import ru.uruydas.model.social.SocialNetworkType;
import ru.uruydas.model.users.User;

public interface SocialNetworkUserService {

    User findOrCreate(SocialNetworkType socialNetworkType, String accessToken);
}
