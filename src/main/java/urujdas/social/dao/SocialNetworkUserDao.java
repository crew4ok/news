package urujdas.social.dao;

import urujdas.model.social.SocialNetworkType;
import urujdas.model.users.User;

public interface SocialNetworkUserDao {

    User findByNetworkId(SocialNetworkType socialNetworkType, Long socialNetworkUserId);

}
