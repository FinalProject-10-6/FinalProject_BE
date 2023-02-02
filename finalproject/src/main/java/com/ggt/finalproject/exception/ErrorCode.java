package com.ggt.finalproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    WRONG_PASSWORD(HttpStatus.BAD_REQUEST.value(),"비밀번호가 일치하지 않습니다."),
    WRONG_ID(HttpStatus.NOT_FOUND.value(),"ID 를 찾을 수 없습니다."),
    OVERLAPPED_LOGINID(HttpStatus.BAD_REQUEST.value(),"중복된 loginID 입니다."),
    OVERLAPPED_EMAIL(HttpStatus.BAD_REQUEST.value(),"중복된 email 입니다."),
    OVERLAPPED_NICKNAME(HttpStatus.BAD_REQUEST.value(),"중복된 nickname 입니다."),

    WRONG_POST(HttpStatus.NOT_FOUND.value(),"해당 글을 찾을 수 없습니다."),
    NO_COMMENT(HttpStatus.NOT_FOUND.value(), "해당 댓글을 찾을 수 없습니다."),

    NOTFOUND_POST(HttpStatus.BAD_REQUEST.value(),"게시글을 찾을 수 없습니다."),
    NOTFOUND_AUTHENTICATION(HttpStatus.BAD_REQUEST.value(),"시큐리티 인증정보를 찾을 수 없습니다."),
    NOAUTH_UPDATE(HttpStatus.BAD_REQUEST.value(),"작성자 본인만 수정 가능합니다."),


    WRONG_EMAIL_CODE(HttpStatus.BAD_REQUEST.value(), "잘못된 이메일 인증번호 입니다."),
    NOTFOUND_EMAIL(HttpStatus.BAD_REQUEST.value(), "등록되지 않은 이메일 입니다"),
    WRONG_EMAIL(HttpStatus.BAD_REQUEST.value(), "이메일이 일치하지 않습니다."),
    WRONG_USERROLE(HttpStatus.BAD_REQUEST.value(),"관리자만 가능한 기능입니다"),
    ERROR(HttpStatus.NO_CONTENT.value(),"알수업는오류"),
    WRONG_FILETYPE(HttpStatus.BAD_REQUEST.value(),"잘못된 파일 확장자입니다"),

    NOT_VALIDURL(HttpStatus.BAD_REQUEST.value(), "알림 url에러"),

    NOT_EXIST_NOTIFICATION(HttpStatus.BAD_REQUEST.value(), "알림 아이디를 찾을수 없음"),
    NOT_VALIDCONTENT(HttpStatus.BAD_REQUEST.value(), "알림 메세지에러");




    private final int statusCode;
    private final String message;
}
