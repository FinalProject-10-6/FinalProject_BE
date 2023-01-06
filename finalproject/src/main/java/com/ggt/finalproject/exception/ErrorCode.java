package com.ggt.finalproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    WRONG_PASSWORD(HttpStatus.BAD_REQUEST.value(),"비밀번호가 일치하지 않습니다."),
    WRONG_ID(HttpStatus.NOT_FOUND.value(),"id 를 찾을 수 없습니다."),
    OVERLAPPED_LOGINID(HttpStatus.BAD_REQUEST.value(),"중복된 loginID 입니다."),
    OVERLAPPED_EMAIL(HttpStatus.BAD_REQUEST.value(),"중복된 email 입니다."),
    OVERLAPPED_NICKNAME(HttpStatus.BAD_REQUEST.value(),"중복된 nickname 입니다."),
    ERROR(HttpStatus.NO_CONTENT.value(),"알수업는오류");




    private final int statusCode;
    private final String message;
}