package com.ggt.finalproject.controller;


import com.ggt.finalproject.dto.LikeResponseDto;
import com.ggt.finalproject.dto.MsgResponseDto;
import com.ggt.finalproject.security.UserDetailsImpl;
import com.ggt.finalproject.service.LikeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Like API"})
@RestController
@RequiredArgsConstructor
public class LikeController {

    public final LikeService likeService;

    @ApiOperation(value = "좋아요 추가/취소")
    @PostMapping("/api/like/post/{postid}")
    public LikeResponseDto likePost(@PathVariable Long postid, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.likePost(postid, userDetails.getUser());
    }
}
