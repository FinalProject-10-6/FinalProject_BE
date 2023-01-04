package com.ggt.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MsgResponseDto {
    private String msg;
    private int statusCode;

    public static MsgResponseDto success(String msg) {
        return new MsgResponseDto(msg, HttpStatus.OK.value());
    }

}
