package com.rent_a_car.agentski_bekend.security.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import javax.security.auth.Subject;

public class TokenBasedAuthentication extends AbstractAuthenticationToken {

    private String token;
    private final UserDetails principle;

    public TokenBasedAuthentication(UserDetails principle){
        super(principle.getAuthorities());
        this.principle = principle;
    }

    public void setToken(String authToken) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return principle;
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }
}
