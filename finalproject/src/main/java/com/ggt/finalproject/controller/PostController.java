package com.ggt.finalproject.controller;

import com.ggt.finalproject.dto.MsgResponseDto;
import com.ggt.finalproject.dto.PostRequestDto;
import com.ggt.finalproject.dto.PostResponseDto;
import com.ggt.finalproject.dto.SearchRequestDto;
import com.ggt.finalproject.entity.Post;
import com.ggt.finalproject.repository.PostRepository;
import com.ggt.finalproject.security.UserDetailsImpl;
import com.ggt.finalproject.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Api(tags = {"Post API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;


    @ApiOperation(value = "게시글 작성")
    @PostMapping("/create")
    public MsgResponseDto createPost(@RequestPart(value = "file") List<MultipartFile> multipartFileList,
                                     @RequestParam("title") String title, @RequestParam("content") String content,
                                     @RequestParam("category") String category, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        PostRequestDto requestDto = new PostRequestDto(title, content, category);
        return postService.createPost(multipartFileList, requestDto, userDetails.getUser());
    }

    // 전체 포스트 가져오기
    @ApiOperation(value = "게시글 전체조회")
    @GetMapping("/postList")
    public List<PostResponseDto> getPosts() {
        return postService.getPosts();
    }
    @ApiOperation(value = "게시글 전체조회")
    @GetMapping("/postList/{category}/{pageNum}")
    public Page<PostResponseDto> getPostsOfCategory(@PathVariable String category, @PathVariable int pageNum) {
        return postService.getPostsOfCategory(category, pageNum);
    }

    // 선택 포스트 가져오기
    @ApiOperation(value = "게시글 상세조회")
    @GetMapping("/{postId}")
    public PostResponseDto getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }

    // 포스트 수정하기
    @ApiOperation(value = "게시글 수정하기")
    @PutMapping("/{postId}")
    public MsgResponseDto updatePost(@PathVariable Long postId,
                                     @RequestPart(value = "file") List<MultipartFile> multipartFileList,
                                     @RequestParam("title") String title, @RequestParam("content") String content,
                                     @RequestParam("category") String category, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        PostRequestDto requestDto = new PostRequestDto(title, content, category);
        return postService.updatePost(multipartFileList, requestDto, userDetails.getUser(), postId);
    }

    // 소프트 딜리트
    @ApiOperation(value = "게시글 삭제")
    @DeleteMapping("/{postId}")
    public MsgResponseDto deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.deletePost(postId, userDetails.getUser());
    }

    @ApiOperation(value = "검색")
    @GetMapping("/search")
    public List<PostResponseDto> searchPost(@RequestParam String keyword) {
        return postService.searchPost(keyword);
    }

}
