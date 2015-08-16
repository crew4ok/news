package ru.uruydas.rememberme.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class RememberMeAuthenticationProvider implements AuthenticationProvider {
    @Override
    public boolean supports(Class<?> authentication) {
        return RememberMeAuthentication.class.equals(authentication);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return authentication;
    }
}
