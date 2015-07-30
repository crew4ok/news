package urujdas.social.service;

import urujdas.model.social.SocialNetworkType;
import urujdas.model.users.User;
import urujdas.social.model.SocialNetworkUser;

public interface SocialNetworkModelConverter {

    User fromSocialNetworkUser(SocialNetworkType socialNetworkType, SocialNetworkUser user);

}
