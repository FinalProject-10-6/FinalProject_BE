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
    private Object nickname;
    private Object profileImg;

    public static LoginResponseDto success(String msg, Object nickname, Object profileImg) {
        return new LoginResponseDto(msg, HttpStatus.OK.value(), nickname, profileImg);
    }

}
