package com.ggt.finalproject.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikeResponseDto {
    private String msg;
    private int statusCode;
    private Object status;

    public static LikeResponseDto success(String msg, Object status) {
        return new LikeResponseDto(msg, HttpStatus.OK.value(), status);
    }
}
