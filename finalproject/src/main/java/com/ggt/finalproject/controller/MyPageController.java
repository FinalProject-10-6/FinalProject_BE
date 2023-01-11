package com.ggt.finalproject.controller;

import com.ggt.finalproject.dto.MsgResponseDto;
import com.ggt.finalproject.dto.MyPageDto;
import com.ggt.finalproject.security.UserDetailsImpl;
import com.ggt.finalproject.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MyPageController {

    private final MyPageService mypageService;


//    @GetMapping("/update/{userId}")
//    public ResponseEntity<?> getMyPage(@PathVariable Long userId){
//        return mypageService.getMyPage(userId);
//    }
//

    @GetMapping("/{userId}")
    public ResponseEntity<?> getMyPage(@PathVariable Long userId, @RequestBody MyPageDto myPageDto){
        return mypageService.getMyPage(userId, myPageDto);
    }

//
//    @PatchMapping("/update/{userId}")
//    public ResponseEntity<?> updateMyPage(@PathVariable Long userId, @RequestBody MyPageDto myPageDto,
//                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return mypageService.updateMyPage(userId, myPageDto, userDetails.getUser());
//    }


}



//    @GetMapping()
//    public ResponseEntity<?> getMyPage(MypageDto mypageDto) throws IOException{
//        return userService.getMypage(mypageDto);
