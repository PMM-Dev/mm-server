package com.kwon770.mm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomAuthenticationException.class)
    protected ResponseEntity<CommonResponse> handleCustomAuthenticationException(CustomAuthenticationException e) {
        CommonResponse response = CommonResponse.builder()
                .code(ErrorCode.AUTHENTICATION_FAILED.getCode())
                .message(e.getMessage())
                .status(ErrorCode.AUTHENTICATION_FAILED.getStatus())
                .build();

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CustomJwtRuntimeException.class)
    protected ResponseEntity<CommonResponse> handleJwtException(CustomJwtRuntimeException e) {
        CommonResponse response = CommonResponse.builder()
                .code(ErrorCode.INVALID_JWT_TOKEN.getCode())
                .message(ErrorCode.INVALID_JWT_TOKEN.getMessage())
                .status(ErrorCode.INVALID_JWT_TOKEN.getStatus())
                .build();

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
