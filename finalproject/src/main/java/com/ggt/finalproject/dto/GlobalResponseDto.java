package com.ggt.finalproject.dto;


import com.ggt.finalproject.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GlobalResponseDto<T> {

    private int statusCode;
    private T msg;


    public static <T> GlobalResponseDto<Object> success(T data, String msg) {
        return new GlobalResponseDto<>(200,msg);
    }

    public static GlobalResponseDto<String> fail(ErrorCode errorCode) {
        return new GlobalResponseDto<>(errorCode.getStatusCode(),errorCode.getMessage());
    }


    public static GlobalResponseDto<String> fail(String msg) {
        return new GlobalResponseDto<>(400,msg);
    }
}
