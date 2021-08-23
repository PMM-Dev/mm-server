package com.kwon770.mm.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    NOT_EXIST_ARGUMENT(400, ""),
    INTERNAL_SERVER_ERROR(500, "PROBLEM OCCURRED FROM SERVER"),
    AUTHENTICATION_FAILED(401, "AUTHENTICATION FAILED"),
    LOGIN_FAILED(401, "LOGIN FAILED"),
    INVALID_JWT_TOKEN(401, "INVALID JWT TOKEN");

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
