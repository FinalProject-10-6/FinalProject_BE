package com.ggt.finalproject.service;

import com.ggt.finalproject.dto.MsgResponseDto;
import com.ggt.finalproject.dto.PostRequestDto;
import com.ggt.finalproject.dto.PostResponseDto;
import com.ggt.finalproject.entity.Post;
import com.ggt.finalproject.entity.TimeStamped;
import com.ggt.finalproject.entity.User;
import com.ggt.finalproject.entity.UserRoleEnum;
import com.ggt.finalproject.jwt.JwtUtil;
import com.ggt.finalproject.repository.LikePostRepository;
import com.ggt.finalproject.repository.PostRepository;
import com.ggt.finalproject.repository.UserRepository;
import com.ggt.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final AWSS3Service awss3Service;
    private final LikePostRepository likePostRepository;

    // 포스트 생성
//    @Transactional
//    public MsgResponseDto createPost(MultipartFile file, PostRequestDto requestDto, User user) throws IOException {
//        String imageFile = null;
//        if (!file.isEmpty()) {
//            imageFile = awss3Service.upload(file, "files");
//        }
//        postRepository.saveAndFlush(new Post(requestDto, user, imageFile));
//        return MsgResponseDto.success("게시글작성완료");
//    }

    // 다중 포스트 생성
    @Transactional
    public MsgResponseDto createPost(List<MultipartFile> multipartFileList, PostRequestDto requestDto, User user) throws IOException {
        List<String> imageFiles = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFileList)
            if (!multipartFile.isEmpty()) {
                String imageFile = null;
                imageFile = awss3Service.upload(multipartFile, "files");
                imageFiles.add(imageFile);
            }
        postRepository.saveAndFlush(new Post(requestDto, user, imageFiles));
        return MsgResponseDto.success("게시글작성완료");
    }

    // 전체 포스트 가져오기
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts() {
        List<PostResponseDto> postList = new ArrayList<>();
        List<Post> posts = postRepository.findAllByPostStatusOrderByCreatedAtDesc(true);
        for(Post post : posts) {
            postList.add(new PostResponseDto(post));
        }
        return postList;
    }
//
//    // 삭제 전체 포스트 가져오기
//    @Transactional(readOnly = true)
//    public List<PostResponseDto> getDeletePosts(User user) {
//
//        List<PostResponseDto> postList = new ArrayList<>();
//        List<Post> posts = postRepository.findAllByPostStatusAndIdOrderByCreatedAtDesc(false, user.getId());
//        for(Post post : posts) {
//            postList.add(new PostResponseDto(post));
//        }
//        return postList;
//    }

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
    public MsgResponseDto deletePost(Long id, User user) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("이미 존재하지 않는 포스트입니다"));
        if(post.getUser().getLoginId().equals(user.getLoginId()) || user.getUserRole().equals(UserRoleEnum.ADMIN) ) {
            post.soft_delete();
            return MsgResponseDto.success("게시글 삭제 완료");
        }
        return MsgResponseDto.fail("게시글 삭제 실패");
    }
}
