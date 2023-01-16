package com.ggt.finalproject.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private String msg;
    private int statusCode;
    private Object loginId;
    private Object nickname;
    private Object profileImg;
    private Object email;

    public static LoginResponseDto success(String msg, Object loginId, Object nickname, Object profileImg, Object email) {
        return new LoginResponseDto(msg, HttpStatus.OK.value(),loginId, nickname, profileImg, email);
    }

}
