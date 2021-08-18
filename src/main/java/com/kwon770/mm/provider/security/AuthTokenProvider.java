package com.kwon770.mm.provider.security;

import java.util.Date;

public interface AuthTokenProvider<T> {
    T createAuthToken(String id, String role);
    T convertAuthToken(String token);
}
