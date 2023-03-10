package com.ggt.finalproject.controller;

import com.ggt.finalproject.dto.*;
import com.ggt.finalproject.entity.User;
import com.ggt.finalproject.repository.UserRepository;
import com.ggt.finalproject.security.UserDetailsImpl;
import com.ggt.finalproject.service.MyPageService;
import com.ggt.finalproject.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
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
            @RequestBody PasswordRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println(userDetails.getPassword());
        return mypageService.checkPW(requestDto, userDetails);
    }
    @PatchMapping("/pwChange")
    public MsgResponseDto changePassword(
            @RequestBody PasswordRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return mypageService.changePW(requestDto, userDetails.getUser());
    }
    @PatchMapping("/socialSetting/{nickname}")
    public LoginResponseDto socialSet(@PathVariable String nickname,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return mypageService.socialSetting(nickname, userDetails.getUser());
    }



    // 상정 마이페이지 내 게시글 가져오기
    @Transactional
    @GetMapping("/myPost/{pageNum}")
    public List<MyPostRepsonseDto> myPost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable int pageNum) {
        return mypageService.myPost(userDetails.getUser(), pageNum - 1 );
    }
    // 마이페이지 내 스크랩 가져오기
    @Transactional
    @GetMapping("/myScrap/{pageNum}")
    public List<MyPostRepsonseDto> myScrap(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable int pageNum) {
        return mypageService.myScrap(userDetails.getUser() , pageNum - 1 );
    }
    // 마이페이지 내 스크랩, 내 게시글 총 갯수 가져오기
    @Transactional
    @GetMapping("/myPostCount")
    public MypageCountDto mypageCount(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return mypageService.mypageCount(userDetails.getUser());
    }
}

