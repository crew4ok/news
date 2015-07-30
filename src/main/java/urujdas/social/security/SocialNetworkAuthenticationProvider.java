package urujdas.social.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import urujdas.model.social.SocialNetworkType;
import urujdas.model.users.User;
import urujdas.social.security.exception.SocialAuthenticationException;
import urujdas.social.service.SocialNetworkUserService;

public class SocialNetworkAuthenticationProvider implements AuthenticationProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocialNetworkAuthenticationProvider.class);

    private final SocialNetworkUserService socialNetworkUserService;

    public SocialNetworkAuthenticationProvider(SocialNetworkUserService socialNetworkUserService) {
        this.socialNetworkUserService = socialNetworkUserService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SocialNetworkUserAuthentication auth = (SocialNetworkUserAuthentication) authentication;

        try {
            SocialNetworkType socialNetworkType = auth.getSocialNetworkType();

            User user = socialNetworkUserService.findOrCreate(socialNetworkType, auth.getAccessToken());

            auth.setUserId(user.getId());

            return auth;
        } catch (Exception e) {
            LOGGER.warn("Exception while authenticating by VK", e);
        }
        throw new SocialAuthenticationException("Authentication failed");
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SocialNetworkUserAuthentication.class.equals(clazz);
    }
}
