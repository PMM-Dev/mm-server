package com.kwon770.mm.exception;

public class SystemIOException extends RuntimeException {
    public SystemIOException(Exception e) {
        super(ErrorCode.SYSTEM_IO_ERROR_MESSAGE);
    }
}
