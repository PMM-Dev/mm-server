package com.kwon770.mm.exception;

public class CustomJwtRuntimeException extends RuntimeException{

    public CustomJwtRuntimeException() {
        super(ErrorCode.INVALID_JWT_TOKEN.getMessage());
    }

    public CustomJwtRuntimeException(String message) {
        super(ErrorCode.INVALID_JWT_TOKEN.getMessage() + " : " + message);
    }

    public CustomJwtRuntimeException(ErrorCode errorCode, String message) {
        super(errorCode.getMessage() + " : " + message);
    }
}
