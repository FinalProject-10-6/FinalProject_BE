package com.ggt.finalproject.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggt.finalproject.dto.KakaoUserInfoDto;
import com.ggt.finalproject.dto.LoginResponseDto;
import com.ggt.finalproject.dto.MsgResponseDto;
import com.ggt.finalproject.dto.TokenDto;
import com.ggt.finalproject.entity.User;
import com.ggt.finalproject.entity.UserRoleEnum;
import com.ggt.finalproject.jwt.JwtUtil;
import com.ggt.finalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

//    https://kauth.kakao.com/oauth/authorize?client_id=0a5a9b8a46f9a0836b9ff04d61ffc21c&redirect_uri=https://dev.d134m2xe6xydy2.amplifyapp.com/user/kakao/callback&response_type=code

    public LoginResponseDto kakaoLogin(String code, HttpServletResponse response) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getToken(code);

        // 2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);

        // 3. 필요시에 회원가입
        User kakaoUser = registerKakaoUserIfNeeded(kakaoUserInfo);

        // 4. JWT 토큰 반환
//        String createToken =  jwtUtil.createToken(kakaoUser.getLoginId(), kakaoUser.getNickname());
        TokenDto tokenDto =  jwtUtil.createAllToken(kakaoUser.getLoginId());
//        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, createToken);

        // 5. set헤더에 넣어주기
        setHeader(response, tokenDto);

//        return createToken;
        return LoginResponseDto.success("로그인 완료",kakaoUser.getLoginId().substring(2), kakaoUser.getNickname(),kakaoUser.getProfileImg(),kakaoUser.getEmail());
    }

    // 1. "인가 코드"로 "액세스 토큰" 요청
    private String getToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "0a5a9b8a46f9a0836b9ff04d61ffc21c");
//        body.add("redirect_uri", "http://localhost:3000/user/kakao/callback");
//        body.add("redirect_uri", "https://dev.d134m2xe6xydy2.amplifyapp.com/user/kakao/callback");
//        body.add("redirect_uri", "https://dev2.d1g5ive02v0psv.amplifyapp.com/user/kakao/callback");
        body.add("redirect_uri", "https://ggultong.co.kr/user/kakao/callback");
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    // 2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String email = jsonNode.get("kakao_account")
                .get("email").asText();
        String profileImg = jsonNode.get("properties")
                .get("profile_image").asText();

        log.info("카카오 사용자 정보: " + id + ", " + nickname + ", " + email + ", " + profileImg);
        return new KakaoUserInfoDto(id, nickname, email, profileImg);
    }

    // 3. 필요시에 회원가입
    private User registerKakaoUserIfNeeded(KakaoUserInfoDto kakaoUserInfo) {
        // DB 에 중복된 Kakao Id 가 있는지 확인
        String loginId = "k_" + kakaoUserInfo.getId().toString();
        User kakaoUser = userRepository.findByLoginId(loginId)
                .orElse(null);
        if (kakaoUser == null) {
            // 카카오 사용자 email 동일한 email 가진 회원이 있는지 확인
            String nickname = kakaoUserInfo.getNickname();
            String kakaoEmail = kakaoUserInfo.getEmail();
            User sameEmailUser = userRepository.findByEmail(kakaoEmail).orElse(null);
            if (sameEmailUser != null) {
                kakaoUser = sameEmailUser;
                // 기존 회원정보에 카카오 Id 추가
                kakaoUser = kakaoUser.kakaoIdUpdate(loginId);
            } else {
                // 신규 회원가입
                // password: random UUID
                String password = UUID.randomUUID().toString();
                nickname = "tlsrbrkdlqwk";
                String encodedPassword = passwordEncoder.encode(password);
                String profileImg = kakaoUserInfo.getProfileImg();

                // email: kakao email
                String email = "k_" + kakaoUserInfo.getEmail();

                kakaoUser = new User(loginId, encodedPassword, email, nickname, profileImg);
            }

            userRepository.save(kakaoUser);
        }
        return kakaoUser;
    }

    // 헤더에 response 둘다 담기
    public void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, tokenDto.getAccessToken());
//        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }
}

//닉네임 재설정 (디비에 있는가?)
