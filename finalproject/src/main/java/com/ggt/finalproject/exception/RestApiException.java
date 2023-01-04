package com.ggt.finalproject.exception;

import com.ggt.finalproject.errcode.StatusCode;
import lombok.Getter;

@Getter
public class RestApiException extends RuntimeException{
    private final StatusCode errorCode;

    public RestApiException(StatusCode errorCode) {
        this.errorCode = errorCode;
    }
}
