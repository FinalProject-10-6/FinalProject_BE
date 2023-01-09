package com.ggt.finalproject.service;

import com.ggt.finalproject.dto.CommentRequestDto;
import com.ggt.finalproject.dto.MsgResponseDto;
import com.ggt.finalproject.entity.Comment;
import com.ggt.finalproject.entity.User;
import com.ggt.finalproject.entity.UserRoleEnum;
import com.ggt.finalproject.errcode.CommonErrorCode;
import com.ggt.finalproject.exception.RestApiException;
import com.ggt.finalproject.repository.CommentRepository;
import jdk.jshell.execution.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CommentService {


    // Post관련 용어에 맞게 정리/수정할 것

    @Transactional
    public MsgResponseDto createComment(Long id, CommentRequestDto commentRequestDto, User user){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RestApiException(CommonErrorCoder.No_Article));
                                                        // 에러코드 뭐할지 결정하고 수정

        Comment comment = new Comment(commentRequestDto, user.getNickname());
        // post 부분 확인하고 수정(createPost)
        comment.createPost(post);
        commentRepository.save(comment);

        // 데이터가 담겨있는 responseDto로 바꿀것
        return new ResponseDto(comment);
    }


    @Transactional
    public MsgResponseDto updateComment(Long id, CommentRequestDto commentRequestDto, User user){
        Comment comment = CommentRepository.findById(id)
                .orElseThrow(()->new RestApiException(CommonErrorCode.NO_ARTICLE));

        // 하단에 유틸 자바클래스에 추가할 것.
        Util.checkCommemntNickname(user, comment);
        comment.update(commentRequestDto);

        //reponseDto 명 변경할 것
        return new ResponseDto(comment);

    }


    @Transactional
    public MsgResponseDto DeleteComment(Long id,User user){
        Comment comment = CommentRepository.findById(id)
                .orElseThrow(()->RestApiException(CommonErrorCode.NO_ARTICLE));

        Util.checkCommentNickname(user, comment);
        comment.delete(comment);
        return MsgResponseDto.success("댓글 삭제 성공");
    }

}


//   // Util 에 추가할 내용
//        public static void checkCommentNickname(User user, Comment comment) {
//            if(!comment.getNickname().equals(user.getNickname())){
//                throw new RestApiException(CommonErrorCode.INVALID_USER);
//            }
//        }