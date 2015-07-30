package urujdas.social.security.exception;

import org.springframework.security.core.AuthenticationException;

public class SocialAuthenticationException extends AuthenticationException {
    public SocialAuthenticationException(String msg) {
        super(msg);
    }
}
