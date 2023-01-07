package com.ggt.finalproject.service;

import com.ggt.finalproject.dto.MsgResponseDto;
import com.ggt.finalproject.dto.PostResponseDto;
import com.ggt.finalproject.entity.Post;
import com.ggt.finalproject.entity.User;
import com.ggt.finalproject.entity.UserRoleEnum;
import com.ggt.finalproject.jwt.JwtUtil;
import com.ggt.finalproject.repository.PostRepository;
import com.ggt.finalproject.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    // 전체 포스트 가져오기
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts() {
        List<PostResponseDto> postList = new ArrayList<>();
        List<Post> posts = postRepository.findAllByOrderByModifiedAtDesc(true);
        for(Post post : posts) {
            postList.add(new PostResponseDto(post));
        }
        return postList;
    }

    // 선택 포스트 가져오기
    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다")
        );
        return new PostResponseDto(post);
    }


    // 소프트 딜리트하기
    @Transactional
    public MsgResponseDto deletePost(Long id, HttpServletRequest request) {
        if(postRepository.existsById(id)){
            Post post;
            Claims claims;

            String token = jwtUtil.resolveToken(request);
            if(jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
                User user = userRepository.findByLoginId(claims.getSubject()).orElseThrow(
                        () -> new IllegalArgumentException("회원을 찾을 수 없습니다")
                );
                UserRoleEnum userRoleEnum = user.getUserRole();
                if(post.getUser().equals(user.getId()) || userRoleEnum.equals(UserRoleEnum.ADMIN)) {
                    postRepository.delete(post);
                }
            }
        } else {
            return new MsgResponseDto("잘못된 토큰값입니다", HttpStatus.BAD_REQUEST.value());
        }
    }


}
