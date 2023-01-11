package com.ggt.finalproject.controller;


import com.ggt.finalproject.dto.LikeResponseDto;
import com.ggt.finalproject.dto.MsgResponseDto;
import com.ggt.finalproject.security.UserDetailsImpl;
import com.ggt.finalproject.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {

    public final LikeService likeService;

    @PostMapping("/api/like/post/{postid}")
    public LikeResponseDto likePost(@PathVariable Long postid, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.likePost(postid, userDetails.getUser());
    }
}
