package com.kwon770.mm.provider.security;

import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtAuthTokenProvider implements AuthTokenProvider<JwtAuthToken> {

    private final Key key;

    public JwtAuthTokenProvider(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    @Override
    public JwtAuthToken createAuthToken(String id, String role) {
        return new JwtAuthToken(id, role, key);
    }

    @Override
    public JwtAuthToken convertAuthToken(String token) {
        return new JwtAuthToken(token, key);
    }
}
