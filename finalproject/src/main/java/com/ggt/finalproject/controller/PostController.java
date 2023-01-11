package com.ggt.finalproject.controller;

import com.ggt.finalproject.dto.MsgResponseDto;
import com.ggt.finalproject.dto.PostRequestDto;
import com.ggt.finalproject.dto.PostResponseDto;
import com.ggt.finalproject.entity.Post;
import com.ggt.finalproject.repository.PostRepository;
import com.ggt.finalproject.security.UserDetailsImpl;
import com.ggt.finalproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;


    @PostMapping("/create")
    public MsgResponseDto createPost(@RequestPart(value = "file") List<MultipartFile> multipartFileList,
                                     @RequestParam("title") String title, @RequestParam("content") String content,
                                     @RequestParam("category") String category, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        PostRequestDto requestDto = new PostRequestDto(title, content, category);
        return postService.createPost(multipartFileList, requestDto, userDetails.getUser());
    }

    // 전체 포스트 가져오기
    @GetMapping("/postList")
    public List<PostResponseDto> getPosts() {
        return postService.getPosts();
    }

    // 선택 포스트 가져오기
    @GetMapping("/{postId}")
    public PostResponseDto getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }


    // 소프트 딜리트
    @DeleteMapping("/{postId}")
    public MsgResponseDto deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.deletePost(postId, userDetails.getUser());
    }
}
