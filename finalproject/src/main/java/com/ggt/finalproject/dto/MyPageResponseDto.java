package com.ggt.finalproject.dto;


import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class MyPageResponseDto {

    private String nickname;

    private String profileImg;

    private String msg;
    private int statusCode;


    public static MyPageResponseDto success(String msg, String nickname, String profileImg) {
        return new MyPageResponseDto(nickname, profileImg, msg, HttpStatus.OK.value());
    }



}
