package ru.uruydas.rememberme.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.uruydas.rememberme.model.PersistentUserSession;

import java.util.Collections;

public class RememberMeAuthentication extends AbstractAuthenticationToken {

    private final PersistentUserSession userSession;

    public RememberMeAuthentication(PersistentUserSession userSession) {
        super(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        this.userSession = userSession;
    }

    @Override
    public Object getPrincipal() {
        return userSession.getUserId();
    }

    @Override
    public Object getCredentials() {
        return userSession.getToken();
    }
}
