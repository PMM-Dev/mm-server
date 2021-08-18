package com.kwon770.mm.exception;

public class CustomJwtRuntimeException extends RuntimeException{

    public CustomJwtRuntimeException() {
        super(ErrorCode.AUTHENTICATION_FAILED.getMessage());
    }

    public CustomJwtRuntimeException(Exception e) {
        super(e.getMessage());
    }
}
