package com.ggt.finalproject.controller;

import com.ggt.finalproject.dto.*;
import com.ggt.finalproject.security.UserDetailsImpl;
import com.ggt.finalproject.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;
import java.util.List;

@Api(tags = {"Post API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;


    @ApiOperation(value = "게시글 작성")
    @PostMapping("/create")
    public PostCreateResponseDto createPost(MultipartHttpServletRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        MultipartFile multipartFile = request.getFile("file");
        System.out.println("멀티파트파일" + multipartFile);
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String category = request.getParameter("category");
        PostRequestDto requestDto = new PostRequestDto(title, content, category);
        return postService.createPost(multipartFile, requestDto, userDetails.getUser());
    }
    @ApiOperation(value = "게시글 url 리턴")
    @PostMapping("/imageUrlReturn")
    public List<String> createPost(@RequestPart(value="file",required = false) List<MultipartFile> multipartFileList) throws IOException {
        System.out.println("multipartFileList = " + multipartFileList.size());
        return postService.imageUrlReturn(multipartFileList);
    }

    @ApiOperation(value = "게시글 전체조회")
    @GetMapping("/postList/{category}/{pageNum}")
    public List<PostResponseDto> getPostsOfCategory(@PathVariable String category, @PathVariable int pageNum) {
        return postService.getPostsOfCategory(category, pageNum-1);
    }
    @ApiOperation(value = "카테고리별 총 게시글 수")
    @GetMapping("/postList/count")
    public CategoryDto getCountOfCategory() {
        return postService.getCountOfCategory();
    }

    // 선택 포스트 가져오기
    @ApiOperation(value = "게시글 상세조회")
    @GetMapping("/{postId}")
    public PostResponseDto getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }

    // 선택 포스트 스크랩하기
    @ApiOperation(value = "게시글 스크랩하기")
    @PostMapping("/scrap/{postId}")
    public ScrapButtonResponseDto scrapPost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long postId) {
        return postService.scrapPost(userDetails.getUser(), postId);
    }

    // 포스트 수정하기
    @ApiOperation(value = "게시글 수정하기")
    @PutMapping("/{postId}")
    public PostCreateResponseDto updatePost(@PathVariable Long postId,
                                     MultipartHttpServletRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        MultipartFile multipartFile = request.getFile("file");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String category = request.getParameter("category");
        PostRequestDto requestDto = new PostRequestDto(title, content, category);
        return postService.updatePost(multipartFile, requestDto, userDetails.getUser(), postId);
    }

    // 소프트 딜리트
    @ApiOperation(value = "게시글 삭제")
    @DeleteMapping("/{postId}")
    public MsgResponseDto deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.deletePost(postId, userDetails.getUser());
    }

    @ApiOperation(value = "검색")
    @GetMapping("/search/{pageNum}")
    public List<PostResponseDto> searchPost(@RequestParam String keyword, @PathVariable int pageNum) {
        return postService.searchPost(keyword, pageNum -1);
    }

//    @ApiOperation(value = "검색")
//    @GetMapping("/search")
//    public List<PostResponseDto> searchPost(@RequestParam String keyword) {
//        return postService.searchPost(keyword);
//    }


    // 이 이후는 음식월드컵 용
    @ApiOperation(value = "좋아요 상위 사진 가져오기")
    @GetMapping("/getWorldcupImage")
    public List<FoodWorldcupResponseDto> getWorldcupImage() {
        return postService.getWorldcupImage();
    }
    @ApiOperation(value = "랭크별 이미지 5개 돌려주기")
    @PostMapping("/getWorldcupImage/{postId}")
    public List<FoodWorldcupResponseDto> worldcupImageRank(@PathVariable Long postId) {
        return postService.worldcupImageRank(postId);
    }
    @ApiOperation(value = "랭크별 이미지 5개 돌려주기 조회")
    @GetMapping("/getWorldcupTop5")
    public List<FoodWorldcupResponseDto> getWorldcupTop5() {
        return postService.getWorldcupTop5();
    }

    @ApiOperation(value = "월 별 우승 준우승 2개 보내주기")
    @GetMapping("/getWorldcupMonth")
    public FoodWorldcupResponseDto[][] getWorldcupMonth() {
        return postService.getWorldcupMonth();
    }


    // 메인페이지
    @ApiOperation(value = "메인페이지 카테고리별 탑6")
    @GetMapping("/likeTop6")
    public MainPagePostResponseDto[][] getLikeTop6() {
        return postService.getLikeTop6();
    }
}
