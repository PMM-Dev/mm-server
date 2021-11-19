package com.kwon770.mm.exception;

public class NotAuthorException extends RuntimeException {

    public NotAuthorException() {
        super(ErrorCode.NOT_AUTHOR_MESSAGE);
    }

    public NotAuthorException(Long currentMemberId) {
        super(ErrorCode.NOT_AUTHOR_MESSAGE + currentMemberId);
    }
}
