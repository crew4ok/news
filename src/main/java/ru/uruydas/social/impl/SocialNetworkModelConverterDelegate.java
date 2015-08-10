package ru.uruydas.social.impl;

import ru.uruydas.model.social.SocialNetworkType;
import ru.uruydas.model.users.User;
import ru.uruydas.social.model.SocialNetworkUser;
import ru.uruydas.social.service.SocialNetworkModelConverter;

import java.util.Map;

public class SocialNetworkModelConverterDelegate implements SocialNetworkModelConverter {

    private final Map<SocialNetworkType, SocialNetworkModelConverter> converters;

    public SocialNetworkModelConverterDelegate(Map<SocialNetworkType, SocialNetworkModelConverter> converters) {
        this.converters = converters;
    }

    @Override
    public User fromSocialNetworkUser(SocialNetworkType socialNetworkType, SocialNetworkUser user) {
        SocialNetworkModelConverter converter = converters.get(socialNetworkType);

        if (converter == null) {
            throw new RuntimeException("Unknown social network type: " + socialNetworkType);
        }

        return converter.fromSocialNetworkUser(socialNetworkType, user);
    }
}
