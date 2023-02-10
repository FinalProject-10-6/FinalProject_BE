package com.ggt.finalproject.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class LoginRequestDto {

    @NotBlank(message = "아이디가 입력되지 않았습니다.")
    private String loginId;

    @NotBlank(message = "비밀번호가 입력되지 않았습니다.")
    private String password;

}
