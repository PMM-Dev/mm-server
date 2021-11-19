package com.kwon770.mm.exception;

import com.kwon770.mm.view.LogView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotAuthorException.class)
    protected ResponseEntity<CommonResponse> handleIllegalArgumentException(final NotAuthorException e) {
        LogView.logInfoExceptionTitle(e);

        CommonResponse response = CommonResponse.builder()
                .status(ErrorCode.NOT_AUTHOR.getStatus())
                .code(ErrorCode.NOT_AUTHOR.name())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ImageIOException.class)
    protected ResponseEntity<CommonResponse> handleIllegalArgumentException(final ImageIOException e) {
        LogView.logInfoExceptionTitle(e);

        CommonResponse response = CommonResponse.builder()
                .status(ErrorCode.IMAGE_IO_ERROR.getStatus())
                .code(ErrorCode.IMAGE_IO_ERROR.name())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<CommonResponse> handleIllegalArgumentException(final IllegalArgumentException e) {
        LogView.logInfoExceptionTitle(e);

        CommonResponse response = CommonResponse.builder()
                .status(ErrorCode.ILLEGAL_ARGUMENT.getStatus())
                .code(ErrorCode.ILLEGAL_ARGUMENT.name())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomAuthenticationException.class)
    protected ResponseEntity<CommonResponse> handleCustomAuthenticationException(final CustomAuthenticationException e) {
        CommonResponse response = CommonResponse.builder()
                .status(ErrorCode.AUTHENTICATION_FAILED.getStatus())
                .code(ErrorCode.AUTHENTICATION_FAILED.name())
                .message(e.getMessage())
                .build();


        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CustomJwtRuntimeException.class)
    protected ResponseEntity<CommonResponse> handleJwtException(final CustomJwtRuntimeException e) {
        CommonResponse response = CommonResponse.builder()
                .status(ErrorCode.INVALID_JWT_TOKEN.getStatus())
                .code(ErrorCode.INVALID_JWT_TOKEN.name())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
