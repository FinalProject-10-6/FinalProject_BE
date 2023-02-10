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
    private Long likePostSum;

    public static LikeResponseDto success(String msg, Object status, Long likePostSum) {
        return new LikeResponseDto(msg, HttpStatus.OK.value(), status, likePostSum);
    }
}
