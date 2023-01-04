package com.ggt.finalproject.controller;


import com.ggt.finalproject.dto.LoginRequestDto;
import com.ggt.finalproject.dto.MsgResponseDto;
import com.ggt.finalproject.dto.SignupRequestDto;
import com.ggt.finalproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public MsgResponseDto signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
    }

    // id 중복체크
    @PostMapping("/idCheck/{loginId}")
    public MsgResponseDto idCheck(@PathVariable String loginId) {
        return userService.idCheck(loginId);
    }

    // 로그인
    @PostMapping("/login")
    public MsgResponseDto login(@RequestBody @Valid LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }
}
