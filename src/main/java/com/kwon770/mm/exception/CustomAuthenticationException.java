package com.kwon770.mm.exception;

public class CustomAuthenticationException extends RuntimeException {

    public CustomAuthenticationException() {
        super(ErrorCode.INVALID_JWT_TOKEN.getMessage());
    }

    public CustomAuthenticationException(String message) {
        super(ErrorCode.INVALID_JWT_TOKEN.getMessage() + " : " + message);
    }
}
