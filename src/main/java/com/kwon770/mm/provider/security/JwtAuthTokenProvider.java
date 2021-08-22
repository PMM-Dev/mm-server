package com.kwon770.mm.provider.security;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
//@PropertySource("classpath:application-secret.properties")
public class JwtAuthTokenProvider implements AuthTokenProvider<JwtAuthToken> {

    @Value("${jwt-secret}")
    public String secret2;
    private final Key key;

    @Autowired
    public JwtAuthTokenProvider(@Value("${jwt-secret}") final String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    @Override
    public JwtAuthToken createAuthToken(String email, String role) {
        return new JwtAuthToken(email, role, key);
    }

    @Override
    public JwtAuthToken convertAuthToken(String token) {
        return new JwtAuthToken(token, key);
    }
}
