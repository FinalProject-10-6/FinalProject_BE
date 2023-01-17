package com.ggt.finalproject.service;

import com.ggt.finalproject.dto.*;
import com.ggt.finalproject.entity.User;
import com.ggt.finalproject.exception.CustomException;
import com.ggt.finalproject.exception.ErrorCode;
import com.ggt.finalproject.repository.UserRepository;
import com.ggt.finalproject.security.UserDetailsImpl;
import com.ggt.finalproject.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPageService {

    private final UserRepository userRepository;

    private final AWSS3Service awss3Service;

    private final PasswordEncoder passwordEncoder;



    @Transactional
    public ResponseEntity<?> getMyPage(User user) {
        return ResponseEntity.ok(new MyPageDto(user));
    }


    @Transactional
    public ResponseEntity<?> updateMyPage(MultipartFile multipartFile, MyPageDto myPageDto, User user) throws IOException {

        // 프로필 사진 업로드
        String profileImg = null;

        if (!multipartFile.isEmpty()){
            profileImg = awss3Service.upload(multipartFile, "profile");
            user.updateMyPage(myPageDto, profileImg);
        }
        user.updateMyPage(myPageDto);
        return ResponseEntity.ok(new MyPageDto(user));
//        return MsgResponseDto.success("정보 수정 완료");
    }


    @Transactional
    public MsgResponseDto deleteUser (Long loginId){
        User user = userRepository.findById(loginId).orElseThrow();

        userRepository.delete(user);

        return MsgResponseDto.success("그동안 서비스를 이용해 주셔서 감사합니다.");
    }


    @Transactional
    public MsgResponseDto checkPW(MyPageDto myPageDto, UserDetailsImpl userDetails) {

        String password = myPageDto.getPassword();

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new CustomException(ErrorCode.WRONG_PASSWORD);
        } else {
            return MsgResponseDto.success("비밀번호가 확인되었습니다.");
        }
    }


    @Transactional
    public MsgResponseDto socialSetting
            (String nickname, String email, String category, UserDetailsImpl userDetails){

        User user = new User(nickname, email, category, userDetails.getUsername(), userDetails.getPassword());
        user.socialUpdate(nickname, email, category, userDetails);
        return MsgResponseDto.success("정보 업데이트 완료");
    }


}
