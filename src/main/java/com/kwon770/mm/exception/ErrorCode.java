package com.kwon770.mm.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    AUTHENTICATION_FAILED(401, "AUTH001", "AUTHENTICATION FAILED"),
    LOGIN_FAILED(401, "AUTH002", "LOGIN FAILED"),
    INVALID_JWT_TOKEN(401, "AUTH003", "INVALID_JWT_TOKEN");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
