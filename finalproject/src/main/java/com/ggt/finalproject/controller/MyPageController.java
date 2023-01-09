package com.ggt.finalproject.controller;

import com.ggt.finalproject.dto.MyPageDto;
import com.ggt.finalproject.security.UserDetailsImpl;
import com.ggt.finalproject.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MyPageController {

    private final MyPageService mypageService;


    @GetMapping("/update/{userId}")
    public ResponseEntity<?> getMyPage(@PathVariable Long userId){
        return mypageService.getMyPage(userId);
    }


    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateMyPage(@PathVariable Long userId, @RequestBody MyPageDto myPageDto,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return mypageService.updateMyPage(userId, myPageDto, userDetails.getUser());
    }


}



//    @GetMapping()
//    public ResponseEntity<?> getMyPage(MypageDto mypageDto) throws IOException{
//        return userService.getMypage(mypageDto);
//    }


//    @GetMapping("/mypage")
//    public String MypageHome(Model model, @AuthenticationPrincipal User user) throws IOException {
//        List<Category> categoryList = categoryService.findAll();
//
//        MypageDto mypageDto = new MypageDto();
//        mypageDto.setNickname(user.getNickname());
//        mypageDto.setPassword(user.getPassword());
//        mypageDto.setEmail(user.getEmail());
//        mypageDto.setProfileIMG(user.getProfileImg());
//
//        model.addAttribute("category", categoryList);
//        model.addAttribute("mypageDto", mypageDto);
//    }


//
//    @PostMapping("/update")
//    public String userEdit(@AuthenticationPrincipal User user, MypageDto requestDto, BindingResult result){
//        if(result.hasErrors())){
//            return "redirect:/mypage/update";
//        }
//
//        userService.updateInfo(user.getUsername(), form.getNickname(), form.getEmail());
//        user.setNickname(form.getNickname());
//        user.setPassword(form.getPassword);
//
//        return "redirect:/mymage/update";
//    }