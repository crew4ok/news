package ru.uruydas.social.impl;

import ru.uruydas.model.social.SocialNetworkType;
import ru.uruydas.social.model.SocialNetworkUser;
import ru.uruydas.social.service.SocialNetworkApi;

import java.util.Map;

public class SocialNetworkApiDelegate implements SocialNetworkApi {

    private final Map<SocialNetworkType, SocialNetworkApi> socialNetworkApis;

    public SocialNetworkApiDelegate(Map<SocialNetworkType, SocialNetworkApi> socialNetworkApis) {
        this.socialNetworkApis = socialNetworkApis;
    }

    @Override
    public SocialNetworkUser getUser(SocialNetworkType socialNetworkType, String accessToken) {
        SocialNetworkApi socialNetworkApi = socialNetworkApis.get(socialNetworkType);

        if (socialNetworkApi == null) {
            throw new RuntimeException("Unknown social network: " + socialNetworkType);
        }

        return socialNetworkApi.getUser(socialNetworkType, accessToken);
    }
}
