package com.kwon770.mm.provider.security;

public interface AuthToken<T> {
    boolean validate();
    T getData();
}
