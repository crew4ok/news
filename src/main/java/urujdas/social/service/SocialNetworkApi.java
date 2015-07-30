package urujdas.social.service;

import urujdas.model.social.SocialNetworkType;
import urujdas.social.model.SocialNetworkUser;

public interface SocialNetworkApi {

    SocialNetworkUser getUser(SocialNetworkType socialNetworkType, String accessToken);

}
