package com.ggt.finalproject.controller;

import com.ggt.finalproject.dto.CommentRequestDto;
import com.ggt.finalproject.dto.CommentResponseDto;
import com.ggt.finalproject.dto.MsgResponseDto;
import com.ggt.finalproject.security.UserDetailsImpl;
import com.ggt.finalproject.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"Comment API"})
@Slf4j
@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @ApiOperation(value = "댓글 작성")
    @PostMapping("/comment/{id}")
    public CommentResponseDto creatComment(
            @PathVariable Long id,
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.createComment(id, requestDto, userDetails.getUser());
    }

    @ApiOperation(value = "댓글 수정")
    @PutMapping("/comment/{id}")
    public CommentResponseDto updateComment(
            @PathVariable Long id,
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.updateComment(id, requestDto, userDetails.getUser());
    }

    @ApiOperation(value = "댓글 삭제")
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
