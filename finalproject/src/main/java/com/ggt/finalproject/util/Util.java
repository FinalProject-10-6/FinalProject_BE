package com.ggt.finalproject.util;

import com.ggt.finalproject.entity.Comment;
import com.ggt.finalproject.entity.Post;
import com.ggt.finalproject.entity.User;
import com.ggt.finalproject.entity.UserRoleEnum;
import com.ggt.finalproject.exception.CustomException;
import com.ggt.finalproject.exception.ErrorCode;
import com.ggt.finalproject.jwt.JwtUtil;
import com.ggt.finalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.PrimitiveIterator;

@Component
@RequiredArgsConstructor
public class Util {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;


    public static void checkCommentNicknameByUser(User user, Comment comment) {
        if (!comment.getNickname().equals(user.getNickname())) {
            throw new CustomException(ErrorCode.WRONG_ID);
        }
    }


}
