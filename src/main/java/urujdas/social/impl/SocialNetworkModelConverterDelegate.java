package urujdas.social.impl;

import urujdas.model.social.SocialNetworkType;
import urujdas.model.users.User;
import urujdas.social.model.SocialNetworkUser;
import urujdas.social.service.SocialNetworkModelConverter;

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
