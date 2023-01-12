package com.ggt.finalproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MyPageDeleteDto<T> {

    private boolean ok;
    private String message;
    private T result;

    public MyPageDeleteDto(boolean ok, String message){
        this.ok = ok;
        this.message = message;
    }

    public MyPageDeleteDto(boolean ok, String message, T result){
        this.ok = ok;
        this.message = message;
        this.result = result;
    }

}
