package com.ggt.finalproject.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class SignupRequestDto {

    @Size(min = 6, max = 10, message = "아이디는 6자 이상 10자 이하만 가능합니다.")
    @Pattern(regexp = "^(?=.*?[0-9])(?=.*?[a-z]).{6,10}$", message = "아이디는 소문자 영문, 숫자 를 필수로 포함하여야 합니다.")
    private String loginId;

    @NotBlank(message = "비밀번호가 입력되지 않았습니다.")
    @Size(min = 8, max = 16)
    @Pattern (regexp="^.(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", message = "비밀번호는 대소문자 영문, 특수문자를 필수로 포함하여야 합니다.")
    private String password;

    @NotBlank(message = "닉네임이 입력되지 않았습니다.")
    @Size(min = 2, max = 8, message = "닉네임은 2자 이상 8자 이하만 가능합니다.")
    @Pattern(regexp = "^[0-9a-zA-Zㄱ-ㅎ가-힣]*$", message = "올바른 닉네임 형식이 아닙니다.")
    private String nickname;


    @NotBlank(message = "이메일이 입력되지 않았습니다.")
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", message = "올바른 이메일 형식이 아닙니다.")
    private String email;

}
