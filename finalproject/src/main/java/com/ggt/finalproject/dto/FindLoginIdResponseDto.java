package com.ggt.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindLoginIdResponseDto {

    private String msg;
    private int statusCode;
    private Object loginId;

}
