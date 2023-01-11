package com.ggt.finalproject.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.ggt.finalproject.dto.LoginRequestDto;
import com.ggt.finalproject.dto.MsgResponseDto;
import com.ggt.finalproject.dto.SignupRequestDto;
import com.ggt.finalproject.jwt.JwtUtil;
import com.ggt.finalproject.service.KakaoService;
import com.ggt.finalproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final KakaoService kakaoService;

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

    // 닉네임 중복체크
    @PostMapping("/nickCheck/{nickname}")
    public MsgResponseDto nickCheck(@PathVariable String nickname) {return userService.nickCheck(nickname);}

    // 로그인
    @PostMapping("/login")
    public MsgResponseDto login(@RequestBody @Valid LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }

    //카카오로그인
    @GetMapping("/kakao/callback")
    public MsgResponseDto kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        // code: 카카오 서버로부터 받은 인가 코드
//        String createToken = kakaoService.kakaoLogin(code, response);
//
//        // Cookie 생성 및 직접 브라우저에 Set
//        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, createToken.substring(7));
//        cookie.setPath("/");
//        response.addCookie(cookie);

        return kakaoService.kakaoLogin(code, response);
        }
    }
