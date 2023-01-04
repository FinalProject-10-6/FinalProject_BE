package com.ggt.finalproject.service;


import com.ggt.finalproject.dto.LoginRequestDto;
import com.ggt.finalproject.dto.MsgResponseDto;
import com.ggt.finalproject.dto.SignupRequestDto;
import com.ggt.finalproject.entity.User;
import com.ggt.finalproject.errcode.UserErrorCode;
import com.ggt.finalproject.exception.RestApiException;
import com.ggt.finalproject.jwt.JwtUtil;
import com.ggt.finalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional    // 회원가입
    public MsgResponseDto signup(SignupRequestDto signupRequestDto) {
        String password;

        // id 중복검사
        if (userRepository.existsByLoginId(signupRequestDto.getLoginId()))
            throw new RestApiException(UserErrorCode.OVERLAPPED_LOGINID);

        // nickname 중복검사
        if (userRepository.existsByNickname(signupRequestDto.getNickname()))
            throw new RestApiException(UserErrorCode.OVERLAPPED_NICKNAME);

        // email 중복검사
        if (userRepository.existsByEmail(signupRequestDto.getEmail()))
            throw new RestApiException(UserErrorCode.OVERLAPPED_EMAIL);

        // password 암호화
        password = passwordEncoder.encode(signupRequestDto.getPassword());

        // DB 저장
        userRepository.save(new User(signupRequestDto.getLoginId(), password, signupRequestDto.getEmail(), signupRequestDto.getNickname()));

        return MsgResponseDto.success("회원가입 성공");
    }

    @Transactional     // id 중복체크
    public MsgResponseDto idCheck(String loginId) {
        Optional<User> found = userRepository.findByLoginId(loginId);
        if (found.isPresent()) {
            throw new RestApiException(UserErrorCode.OVERLAPPED_LOGINID);
        }
        return MsgResponseDto.success("사용 가능한 아이디 입니다.");
    }

    @Transactional
    public MsgResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String loginId = loginRequestDto.getLoginId();
        String password = loginRequestDto.getPassword();

        // id 가 틀림
        User user = userRepository.findByLoginId(loginId).orElseThrow(
                () -> new RestApiException(UserErrorCode.WRONG_ID)
        );

        // password 가 틀림
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RestApiException(UserErrorCode.WRONG_PASSWORD);
        }

        // 헤더에  id, role 담은 토큰 추가
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getLoginId(), user.getUserRole()));

        return MsgResponseDto.success("로그인 성공");
    }


}
