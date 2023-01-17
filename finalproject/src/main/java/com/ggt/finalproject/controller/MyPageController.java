package com.ggt.finalproject.controller;

import com.ggt.finalproject.dto.MsgResponseDto;
import com.ggt.finalproject.dto.MyPageDto;
import com.ggt.finalproject.entity.User;
import com.ggt.finalproject.repository.UserRepository;
import com.ggt.finalproject.security.UserDetailsImpl;
import com.ggt.finalproject.service.MyPageService;
import com.ggt.finalproject.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Api(tags = {"MyPage API"})
@Slf4j
@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService mypageService;



    @ApiOperation(value = "마이페이지")
    @GetMapping("")
    public ResponseEntity<?> getMyPage(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return mypageService.getMyPage(userDetails.getUser());
    }


    @PatchMapping("/update")
    public ResponseEntity<?> updateMyPage(
            @RequestPart(value = "profileImg") MultipartFile multipartFile,
            @RequestParam("nickname") String nickname, @RequestParam("password") String password,
            @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return mypageService.updateMyPage(multipartFile, nickname, password, userDetails.getUser());
    }


    @DeleteMapping("/{loginId}")
    public MsgResponseDto deleteMyPage(@PathVariable Long loginId){
        return mypageService.deleteUser(loginId);
    }


    @PostMapping("/pwCheck")
    public MsgResponseDto checkPassword(
            @RequestBody MyPageDto myPageDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println(myPageDto.getPassword());
        System.out.println(userDetails.getPassword());
        return mypageService.checkPW(myPageDto, userDetails);
    }



    @PatchMapping("/socialSetting")
    public MsgResponseDto socialSet(
            @RequestParam("nickname") String nickname,
            @RequestParam("email") String email,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return mypageService.socialSetting(nickname, email, userDetails);
    }

//    @PatchMapping("/socialSetting/{loginId}")
//    public MsgResponseDto socialSet(Long loginId,
//            @RequestParam("nickname") String nickname,
//            @RequestParam("email") String email,
//            @RequestParam("category") String category){
//        return mypageService.socialSetting(loginId, nickname, email, category);
//    }



    }

//    @PutMapping("/{userId}")
//    public MyPageDeleteDto deleteUser (
//            @PathVariable Long userId,
//            @AuthenticationPrincipal UserDetailsImpl userDetails){
//        User user = userRepository.findByLoginId(userDetails.getLoginId())
//                .orElseThrow(()->new CustomException(ErrorCode.WRONG_ID));
//
//        return mypageService.deleteUser(user);
//    }