package com.ggt.finalproject.controller;

import com.ggt.finalproject.dto.MsgResponseDto;
import com.ggt.finalproject.dto.MyPageDto;
import com.ggt.finalproject.dto.MyPageResponseDto;
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
import java.util.List;

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
    public MyPageResponseDto updateMyPage(
            @RequestPart(value = "profileImg") List<MultipartFile> multipartFileList,
            @RequestParam("nickname") String nickname,
            @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return mypageService.updateMyPage(multipartFileList, nickname, userDetails.getUser());
    }


    @DeleteMapping("/{loginId}")
    public MsgResponseDto deleteMyPage(
            @PathVariable String loginId,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return mypageService.deleteUser(loginId, userDetails.getUser());
    }


    @PostMapping("/pwCheck")
    public MsgResponseDto checkPassword(
            @RequestBody MyPageDto myPageDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println(myPageDto.getPassword());
        System.out.println(userDetails.getPassword());
        return mypageService.checkPW(myPageDto, userDetails);
    }

    @PatchMapping("/pwChange")
    public MsgResponseDto changePassword(
            @RequestParam("password") String password,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return mypageService.changePW(password, userDetails.getUser());
    }



    @PatchMapping("/socialSetting")
    public MsgResponseDto socialSet(
            @RequestParam("nickname") String nickname,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return mypageService.socialSetting(nickname, userDetails.getUser());
    }

    }

