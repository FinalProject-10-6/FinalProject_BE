package com.ggt.finalproject.service;

import com.ggt.finalproject.dto.CommentRequestDto;
import com.ggt.finalproject.dto.CommentResponseDto;
import com.ggt.finalproject.dto.MsgResponseDto;
import com.ggt.finalproject.entity.*;
import com.ggt.finalproject.exception.CustomException;
import com.ggt.finalproject.exception.ErrorCode;
import com.ggt.finalproject.jwt.JwtUtil;
import com.ggt.finalproject.repository.CommentRepository;
import com.ggt.finalproject.repository.PostRepository;
import com.ggt.finalproject.repository.UserRepository;
import com.ggt.finalproject.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final NotificationService notificationService;


//    @Transactional
//    public List<CommentResponseDto> getComment(int page, int size, String sortBy, boolean isAsc){
//
//        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
//        Sort sort = Sort.by(direction, sortBy);
//        Pageable pageable = PageRequest.of(page, size, sort);
//
//
//        return commentRepository.findAllByOrderByCreatedAtDesc(pageable).stream()
//                .map(CommentResponseDto::new)
//                .collect(Collectors.toList());
//    }



    @Transactional
    public CommentResponseDto createComment(Long id, CommentRequestDto requestDto, User user){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.WRONG_POST));

        Comment comment = new Comment(requestDto, user);
        comment.updatePost(post);
        commentRepository.save(comment);

        //?????? ????????? ???????????? url
//        String Url = "https://tom-jelly.shop/api/post/"+post.getId();
        String Url = "/"+post.getCategory()+"List/"+post.getCategory()+"/"+"detail/"+post.getId();
        //?????? ?????? ??? ????????? ?????? ???????????? ????????? ?????? ?????? ,
        String content = post.getUser().getNickname()+"???! ????????? ?????? ????????? ???????????????!";

        //????????? ???????????? ????????? ???????????? ????????? ?????? ????????? ??????.
        if(!Objects.equals(user.getId(), post.getUser().getId())) {
            notificationService.send(post.getUser(), NotificationType.REPLY, content, Url);
        }

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
        return MsgResponseDto.success("?????? ?????? ??????");
    }


}

