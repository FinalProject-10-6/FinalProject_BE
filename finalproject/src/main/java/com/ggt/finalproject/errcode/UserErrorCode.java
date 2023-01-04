package com.ggt.finalproject.errcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    USER_SIGNUP_SUCCESS("회원가입 성공", HttpStatus.OK.value()),
    WRONG_USERNAME_PATTERN("유저명은 최소 6자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)로 구성되어야 합니다.", HttpStatus.BAD_REQUEST.value()),
    WRONG_PASSWORD_PATTERN("비밀번호는 최소 8자 이상, 16자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자로 구성되어야 합니다.", HttpStatus.BAD_REQUEST.value()),
    WRONG_PASSWORD("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST.value()),
    WRONG_ID("id 를 찾을 수 없습니다.",HttpStatus.NOT_FOUND.value()),
    OVERLAPPED_LOGINID("중복된 loginID 입니다.", HttpStatus.BAD_REQUEST.value()),
    OVERLAPPED_EMAIL("중복된 email 입니다.", HttpStatus.BAD_REQUEST.value()),
    OVERLAPPED_NICKNAME("중복된 nickname 입니다.", HttpStatus.BAD_REQUEST.value());

    private final String msg;
    private final int statusCode;



}
