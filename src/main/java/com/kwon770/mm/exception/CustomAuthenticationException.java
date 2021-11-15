package com.kwon770.mm.exception;

public class CustomAuthenticationException extends RuntimeException {

    public CustomAuthenticationException() {
        super(ErrorCode.AUTHENTICATION_FAILED.name());
    }

    public CustomAuthenticationException(String message) {
        super(ErrorCode.AUTHENTICATION_FAILED.name() + " : " + message);
    }
}
