package com.kwon770.mm.exception;

public class CustomJwtRuntimeException extends RuntimeException{

    public CustomJwtRuntimeException() {
        super(ErrorCode.AUTHENTICATION_FAILED.getMessage());
    }

    public CustomJwtRuntimeException(String message) {
        super(ErrorCode.AUTHENTICATION_FAILED.getMessage() + " : " + message);
    }
}
