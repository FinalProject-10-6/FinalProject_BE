package com.ggt.finalproject.service;

import com.ggt.finalproject.dto.MsgResponseDto;
import com.ggt.finalproject.dto.PostRequestDto;
import com.ggt.finalproject.dto.PostResponseDto;
import com.ggt.finalproject.entity.Post;
import com.ggt.finalproject.entity.User;
import com.ggt.finalproject.entity.UserRoleEnum;
import com.ggt.finalproject.jwt.JwtUtil;
import com.ggt.finalproject.repository.PostRepository;
import com.ggt.finalproject.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    private final AWSS3Service awss3Service;

    // 포스트 생성
    @Transactional
    public MsgResponseDto createPost(MultipartFile file, PostRequestDto requestDto, HttpServletRequest request) throws IOException {
        Claims claims;
        String token = jwtUtil.resolveToken(request);
        if (jwtUtil.validateToken(token)) {
            claims = jwtUtil.getUserInfoFromToken(token);
            User user = userRepository.findByLoginId(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("회원을 찾을 수 없습니다")
            );
            String imageFile = null;
            if (!file.isEmpty()) {
                imageFile = awss3Service.upload(file, "files");
            }
            postRepository.saveAndFlush(new Post(requestDto, user, imageFile));
            return MsgResponseDto.success("게시글작성완료");
        }
        return MsgResponseDto.fail("토큰값이 잘못되었습니다");
    }


    // 전체 포스트 가져오기
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts() {
        List<PostResponseDto> postList = new ArrayList<>();
        List<Post> posts = postRepository.findAllByOrderByModifiedAtDesc();
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
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("이미 존재하지 않는 포스트입니다")
        );
        Claims claims;
        String token = jwtUtil.resolveToken(request);
        if (jwtUtil.validateToken(token)) {
            claims = jwtUtil.getUserInfoFromToken(token);
            User user = userRepository.findByLoginId(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("회원을 찾을 수 없습니다")
            );
            UserRoleEnum userRoleEnum = user.getUserRole();
            if (post.getUser().equals(user.getId()) || userRoleEnum.equals(UserRoleEnum.ADMIN)) {
                postRepository.delete(post);
                return MsgResponseDto.success("게시글삭제가 완료되었습니다");
            }
        }
            return MsgResponseDto.fail("토큰값이 잘못되었습니다");
    }
}
