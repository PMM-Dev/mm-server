package com.kwon770.mm.exception;

public class CustomJwtRuntimeException extends RuntimeException{

    public CustomJwtRuntimeException() {
        super(ErrorCode.INVALID_JWT_TOKEN.name());
    }

    public CustomJwtRuntimeException(String message) {
        super(ErrorCode.INVALID_JWT_TOKEN.name() + " : " + message);
    }

    public CustomJwtRuntimeException(ErrorCode errorCode, String message) {
        super(errorCode.name() + " : " + message);
    }
}
