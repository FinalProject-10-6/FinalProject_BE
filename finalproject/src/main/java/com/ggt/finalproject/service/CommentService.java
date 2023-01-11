package com.ggt.finalproject.service;

import com.ggt.finalproject.dto.CommentRequestDto;
import com.ggt.finalproject.dto.CommentResponseDto;
import com.ggt.finalproject.dto.MsgResponseDto;
import com.ggt.finalproject.entity.Comment;
import com.ggt.finalproject.entity.Post;
import com.ggt.finalproject.entity.User;
import com.ggt.finalproject.entity.UserRoleEnum;
import com.ggt.finalproject.exception.CustomException;
import com.ggt.finalproject.exception.ErrorCode;
import com.ggt.finalproject.jwt.JwtUtil;
import com.ggt.finalproject.repository.CommentRepository;
import com.ggt.finalproject.repository.PostRepository;
import com.ggt.finalproject.repository.UserRepository;
import com.ggt.finalproject.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class CommentService {

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public CommentService(PostRepository postRepository, CommentRepository commentRepository, UserRepository userRepository, JwtUtil jwtUtil){
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;

    }


    @Transactional
    public CommentResponseDto createComment(Long id, CommentRequestDto requestDto, User user){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.WRONG_POST));

        Comment comment = new Comment(requestDto, user.getNickname());
        comment.updatePost(post);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }


    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto, User user){
        Comment comment = commentRepository.findById(id)
                .orElseThrow(()->new CustomException(ErrorCode.NO_COMMENT));

        Util.checkCommentNicknameByUser(user, comment);
        comment.update(requestDto);

        return new CommentResponseDto(comment);

    }


    @Transactional
    public MsgResponseDto deleteComment(Long id,User user){
        Comment comment = commentRepository.findById(id)
                .orElseThrow(()->new CustomException(ErrorCode.NO_COMMENT));

        Util.checkCommentNicknameByUser(user, comment);
        commentRepository.delete(comment);
        return MsgResponseDto.success("댓글 삭제 성공");
    }


}

