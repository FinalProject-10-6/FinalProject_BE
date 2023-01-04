package com.ggt.finalproject.exception;

import com.ggt.finalproject.errcode.ErrorCode;
import lombok.Getter;

@Getter
public class RestApiException extends RuntimeException{
    private final ErrorCode errorCode;

    public RestApiException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
