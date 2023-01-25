package com.ggt.finalproject.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggt.finalproject.dto.MsgResponseDto;
import com.ggt.finalproject.dto.NaverUserInfoDto;
import com.ggt.finalproject.dto.TokenDto;
import com.ggt.finalproject.entity.User;
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
public class NaverService {
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;


//    https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=8PCgO32YgjQK0j2o2102&state=state&redirect_uri=https://dev.d134m2xe6xydy2.amplifyapp.com/user/naver/callback
//    https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=CLIENT_ID&state=STATE_STRING&redirect_uri=CALLBACK_URL
//    https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=8PCgO32YgjQK0j2o2102&redirect_uri=http://43.201.7.130:8080/api/user/naver/callback&state=state

    public MsgResponseDto naverLogin(String code, String state, HttpServletResponse response) throws JsonProcessingException {
        // 인가코드, state 로 네이버한테 access_token 요청
        String accessToken = getToken(code, state);

        // access_token 으로 사용자 정보 가져오기
        NaverUserInfoDto naverUserInfoDto = getNaverUserinfo(accessToken);

        // 회원가입
        User naverUser = registerNaverUser(naverUserInfoDto);

        // 토큰 헤더에 담기
        TokenDto tokenDto = jwtUtil.createAllToken(naverUser.getLoginId());
        setHeader(response, tokenDto);

        return MsgResponseDto.success("네이버 로그인 완료");
    }

    private String getToken(String code, String state) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "8PCgO32YgjQK0j2o2102");
        params.add("client_secret", "0Q1eBdkdPO");
        params.add("code", code);
        params.add("state", state);

        HttpEntity<MultiValueMap<String, String>> naverTokenRequest =
                new HttpEntity<>(params, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> naverResponse = rt.exchange(
                "https://nid.naver.com/oauth2.0/token",
                HttpMethod.POST,
                naverTokenRequest,
                String.class
        );

        String responseBody = naverResponse.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    private NaverUserInfoDto getNaverUserinfo(String accessToken) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> naverUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.POST,
                naverUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper2 = new ObjectMapper();
        JsonNode jsonNode2 = objectMapper2.readTree(responseBody);

        Long id = jsonNode2.get("response").get("id").asLong();
        String profileImage = jsonNode2.get("response").get("profile_image").asText();
        String email = jsonNode2.get("response").get("email").asText();
        String nickname = jsonNode2.get("response").get("nickname").asText();

        log.info("네이버 사용자 정보: " + id + ", " + nickname + ", " + email + ", ");
        return new NaverUserInfoDto(id, nickname, email, profileImage);
    }

    private User registerNaverUser(NaverUserInfoDto naverUserInfoDto){
        String loginId = "n_" + naverUserInfoDto.getId().toString();
        User naverUser = userRepository.findByLoginId(loginId)
                .orElse(null);

        if (naverUser == null) {
            String nickname = naverUserInfoDto.getNickname();
            String naverEmail = naverUserInfoDto.getEmail();
            User sameEmailUser = userRepository.findByEmail(naverEmail).orElse(null);
            if (sameEmailUser != null) {
                naverUser = sameEmailUser;
                naverUser = naverUser.naverIdUpdate(loginId);
            } else {
                String password = UUID.randomUUID().toString();
                String encodePassword = passwordEncoder.encode(password);
                String profileImg = naverUserInfoDto.getProfileImg();
                String email = "n_" + naverUserInfoDto.getEmail();

                naverUser = new User(loginId, encodePassword, email, nickname, profileImg);
            }
            userRepository.save(naverUser);
        }
        return naverUser;
    }

    public void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, tokenDto.getAccessToken());
//        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }


}
