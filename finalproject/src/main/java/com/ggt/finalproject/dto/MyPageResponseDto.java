package com.ggt.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyPageResponseDto {

    private String nickname;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    private String password;

    private String profileImg;

    private String msg;
    private int statusCode;


    public static MyPageResponseDto success(String nickname, String password, String profileImg, String msg) {
        return new MyPageResponseDto(nickname, password, profileImg, msg, HttpStatus.OK.value());

    }




}