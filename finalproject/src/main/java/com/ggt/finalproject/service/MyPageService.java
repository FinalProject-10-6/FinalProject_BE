package com.ggt.finalproject.service;

import com.ggt.finalproject.dto.MsgResponseDto;
import com.ggt.finalproject.dto.MyPageDeleteDto;
import com.ggt.finalproject.dto.MyPageDto;
import com.ggt.finalproject.entity.User;
import com.ggt.finalproject.exception.CustomException;
import com.ggt.finalproject.exception.ErrorCode;
import com.ggt.finalproject.repository.UserRepository;
import com.ggt.finalproject.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPageService {

    private final UserRepository userRepository;

//    private final FileProcessService fileProcessService;


    @Transactional
    public ResponseEntity<?> getMyPage(User user) {
        return ResponseEntity.ok(new MyPageDto(user));
    }

//    @Transactional
//    public ResponseEntity<?> updateMyPage(Long id, MyPageDto myPageDto, User user) {
//
////            User user = userRepository.findById(Id).orElseThrow();
//        String profileImg = null;
//        if (!myPageDto.getFile().isEmpty()){
//            String filename = user.getProfileImg().split(".com/")[1];
//            fileProcessService.deleteFile(filename);
//            profileImg = fileProcessService.uploadFile(myPageDto.getFile());
//        }
//        user.updateMyPage(myPageDto, profileImg);
//        return ResponseEntity.ok(new MyPageDto(user));
//    }


//    public MyPageDeleteDto deleteUser(User user){
//        user.setStatus(false);
//        userRepository.save(user);
//        return new MyPageDeleteDto(true, "그동안 서비스를 이용해 주셔서 감사합니다.");
//    }
}





//
//    public ResponseEntity<?> getMyPage(MypageDto mypageDto) throws IOException{
