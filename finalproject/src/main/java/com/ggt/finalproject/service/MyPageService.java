//package com.ggt.finalproject.service;
//
//import com.ggt.finalproject.dto.MsgResponseDto;
//import com.ggt.finalproject.dto.MyPageDto;
//import com.ggt.finalproject.entity.User;
//import com.ggt.finalproject.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class MyPageService {
//
//    private final UserRepository userRepository;
//
//    private final FileProcessService fileProcessService;
//
//
//    @Transactional
//    public ResponseEntity<?> getMyPage(Long id) {
//        if (!userRepository.existsById(id)) {
//            return ResponseEntity.ok(new MsgResponseDto("회원이 아닙니다.", HttpStatus.BAD_REQUEST.value()));
//        }
//        User user = userRepository.findById(id).orElseThrow();
//        return ResponseEntity.ok(new MyPageDto(user));
//    }
//
//    @Transactional
//    public ResponseEntity<?> updateMyPage(Long id, MyPageDto myPageDto, User user) {
//
////            User user = userRepository.findById(Id).orElseThrow();
//            String profileImg = null;
//            if (!myPageDto.getFile().isEmpty()){
//                String filename = user.getProfileImg().split(".com/")[1];
//                fileProcessService.deleteFile(filename);
//                profileImg = fileProcessService.uploadFile(myPageDto.getFile());
//            }
//            user.updateMyPage(myPageDto, profileImg);
//            return ResponseEntity.ok(new MyPageDto(user));
//    }
//}
//
//
//
//
//
//        //
////    public ResponseEntity<?> getMyPage(MypageDto mypageDto) throws IOException{
////
////        Long userId = Long.valueOf(mypageDto.getNickname());
////
////        List<User> users = userRepository.findById(userId).get().
////
////    }
////
////        Optional<User> = userRepository.findById(userId);
////        user.updateMyPage(mypageDto);
////
////        return ResponseEntity.ok(new MsgResponseDto("회원정보 수정 완료", HttpStatus.OK.value()));
////    }
//
//
//
//
