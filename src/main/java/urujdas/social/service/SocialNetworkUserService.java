package urujdas.social.service;

import urujdas.model.social.SocialNetworkType;
import urujdas.model.users.User;

public interface SocialNetworkUserService {

    User findOrCreate(SocialNetworkType socialNetworkType, String accessToken);
}
