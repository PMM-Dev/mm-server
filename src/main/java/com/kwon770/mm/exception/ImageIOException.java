package com.kwon770.mm.exception;

public class ImageIOException extends RuntimeException {
    public ImageIOException() {
        super(ErrorCode.IMAGE_IO_ERROR_MESSAGE);
    }

    public ImageIOException(Exception e) {
        super(ErrorCode.IMAGE_IO_ERROR_MESSAGE + e.getMessage());
        e.printStackTrace();
    }
}
