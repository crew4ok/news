package ru.uruydas.social.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.uruydas.social.model.SocialNetworkType;

import java.util.Collections;

public class SocialNetworkUserAuthentication extends AbstractAuthenticationToken {
    private Long userId ;
    private String accessToken;
    private SocialNetworkType socialNetworkType;

    @JsonCreator
    public SocialNetworkUserAuthentication(@JsonProperty("socialNetworkType") SocialNetworkType socialNetworkType,
                                           @JsonProperty("accessToken") String accessToken) {
        super(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        this.socialNetworkType = socialNetworkType;
        this.accessToken = accessToken;
    }

    @Override
    public Object getCredentials() {
        return accessToken;
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public SocialNetworkType getSocialNetworkType() {
        return socialNetworkType;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setSocialNetworkType(SocialNetworkType socialNetworkType) {
        this.socialNetworkType = socialNetworkType;
    }
}
