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

    WRONG_POST(HttpStatus.NOT_FOUND.value(),"해당 글을 찾을 수 없습니다."),
    NO_COMMENT(HttpStatus.NOT_FOUND.value(), "해당 댓글을 찾을 수 없습니다."),

    NOTFOUND_POST(HttpStatus.BAD_REQUEST.value(),"게시글을 찾을 수 없습니다."),
    NOTFOUND_AUTHENTICATION(HttpStatus.BAD_REQUEST.value(),"시큐리티 인증정보를 찾을 수 없습니다."),


    WRONG_EMAIL_CODE(HttpStatus.BAD_REQUEST.value(), "잘못된 이메일 인증번호 입니다."),

    ERROR(HttpStatus.NO_CONTENT.value(),"알수업는오류"),
    WRONG_FILETYPE(HttpStatus.BAD_REQUEST.value(),"잘못된 파일 확장자입니다");




    private final int statusCode;
    private final String message;
}
