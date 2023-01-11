package com.ggt.finalproject.controller;

import com.ggt.finalproject.dto.CommentRequestDto;
import com.ggt.finalproject.dto.CommentResponseDto;
import com.ggt.finalproject.dto.MsgResponseDto;
import com.ggt.finalproject.security.UserDetailsImpl;
import com.ggt.finalproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping("/comment/{id}")
    public CommentResponseDto creatComment(
            @PathVariable Long id,
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.createComment(id, requestDto, userDetails.getUser());
    }

    @PutMapping("/comment/{id}")
    public CommentResponseDto updateComment(
            @PathVariable Long id,
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.updateComment(id, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/comment/{id}")
    public MsgResponseDto deleteComment(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.deleteComment(id, userDetails.getUser());
    }


//    public static void checkCommentNickname(User user, Comment comment) {
//        if(!comment.getNickname().equals(user.getNickname())){
//            throw new CustomException(ErrorCode.WRONG_ID);
//            }
//        }

}
