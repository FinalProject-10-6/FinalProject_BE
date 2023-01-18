package com.ggt.finalproject.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter

public class FindPwRequestDto {

    @NotBlank(message = "이메일이 입력되지 않았습니다.")
    @Size(min = 6, max = 10, message = "아이디는 6자 이상 10자 이하만 가능합니다.")
    @Pattern(regexp = "^(?=.*?[0-9])(?=.*?[a-z]).{6,10}$", message = "아이디는 소문자 영문, 숫자 를 필수로 포함하여야 합니다.")
    private String loginId;

    @NotBlank(message = "이메일이 입력되지 않았습니다.")
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", message = "올바른 이메일 형식이 아닙니다.")
    private String email;
}
