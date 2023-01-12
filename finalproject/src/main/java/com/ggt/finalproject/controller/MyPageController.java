package com.ggt.finalproject.controller;

import com.ggt.finalproject.dto.MsgResponseDto;
import com.ggt.finalproject.dto.MyPageDeleteDto;
import com.ggt.finalproject.dto.MyPageDto;
import com.ggt.finalproject.entity.User;
import com.ggt.finalproject.exception.CustomException;
import com.ggt.finalproject.exception.ErrorCode;
import com.ggt.finalproject.repository.UserRepository;
import com.ggt.finalproject.security.UserDetailsImpl;
import com.ggt.finalproject.service.MyPageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@Api(tags = {"MyPage API"})
@Slf4j
@RestController
@RequestMapping("/api/mypage")
public class MyPageController {
    private final UserRepository userRepository;

    private final MyPageService mypageService;

    public MyPageController(MyPageService mypageService,
                            UserRepository userRepository) {this.mypageService = mypageService;
        this.userRepository = userRepository;
    }


//    @GetMapping("/update/{userId}")
//    public ResponseEntity<?> getMyPage(@PathVariable Long userId){
//        return mypageService.getMyPage(userId);
//    }


//    @ApiOperation(value = "마이페이지")
//    @GetMapping("/{userId}")
//    public ResponseEntity<?> getMyPage(@PathVariable Long userId, @RequestBody MyPageDto myPageDto){
//        return mypageService.getMyPage(userId, myPageDto);
//    }


    @ApiOperation(value = "마이페이지")
    @GetMapping("")
    public ResponseEntity<?> getMyPage(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return mypageService.getMyPage(userDetails.getUser());
    }


//    @PatchMapping("/update/{userId}")
//    public ResponseEntity<?> updateMyPage(@PathVariable Long userId, @RequestBody MyPageDto myPageDto,
//                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return mypageService.updateMyPage(userId, myPageDto, userDetails.getUser());
//    }


//    @PutMapping("/{userId}")
//    public MyPageDeleteDto deleteUser (
//            @PathVariable Long userId,
//            @AuthenticationPrincipal UserDetailsImpl userDetails){
//        User user = userRepository.findByLoginId(userDetails.getLoginId())
//                .orElseThrow(()->new CustomException(ErrorCode.WRONG_ID));
//
//        return mypageService.deleteUser(user);
//    }
    }


//    @GetMapping()
//    public ResponseEntity<?> getMyPage(MypageDto mypageDto) throws IOException{
//        return userService.getMypage(mypageDto);
