package com.ggt.finalproject.service;

import com.ggt.finalproject.dto.*;
import com.ggt.finalproject.entity.*;
import com.ggt.finalproject.exception.CustomException;
import com.ggt.finalproject.exception.ErrorCode;
import com.ggt.finalproject.repository.*;

import com.ggt.finalproject.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.bytebuddy.asm.Advice;
import nonapi.io.github.classgraph.json.JSONUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final AWSS3Service awss3Service;
    private final LikePostRepository likePostRepository;
    private final WorldCupRepository worldCupRepository;
    private final ScrapPostRepository scrapPostRepository;

//     포스트 이미지 url 리턴용
    @Transactional
    public List<String> imageUrlReturn(List<MultipartFile> multipartFileList) throws IOException {
        List<String> imageUrl = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFileList) {
            if (!multipartFile.isEmpty()) {
                String imageFile = null;
                imageFile = awss3Service.upload(multipartFile, "files");
                System.out.println(imageFile);
                imageUrl.add(imageFile);
            }
        }
        return imageUrl;
    }

    // 다중 포스트 생성
    @Transactional
    public PostCreateResponseDto createPost(MultipartFile multipartFile, PostRequestDto requestDto, User user) throws IOException {

        if (!(multipartFile == null)) {
            String imageFile = null;
            imageFile = awss3Service.upload(multipartFile, "files");
            Post post = postRepository.saveAndFlush(new Post(requestDto, user, imageFile));
            return new PostCreateResponseDto(post, user, "게시글 작성 완료");
        } else {
            Post post = postRepository.saveAndFlush(new Post(requestDto, user));
            return new PostCreateResponseDto(post, user, "게시글 작성 완료");
    }
    }

    // 포스트 수정하기
    @Transactional
    public PostCreateResponseDto updatePost(MultipartFile multipartFile, PostRequestDto requestDto, User user, Long id) throws IOException {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 포스트입니다"));
        if (post.getUser().getLoginId().equals(user.getLoginId())) {
            if (!(multipartFile == null)) {
                String imageFile = null;
                imageFile = awss3Service.upload(multipartFile, "files");
                post.update(requestDto, imageFile);
            } else {
                post.update(requestDto);
            }
            return new PostCreateResponseDto(post, user, "게시글 수정 완료");
        } else {
            throw new CustomException(ErrorCode.NOAUTH_UPDATE);
        }
    }

    // 카테고리별 포스트 가져오기
    // 전체 포스트 가져오기
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostsOfCategory(String category, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, 10);
        List<PostResponseDto> postList = new ArrayList<>();
        Page<Post> posts = postRepository.findAllByPostStatusAndCategoryOrderByCreatedAtDesc(pageable, true, category);
        for(Post post : posts) {
            Long commentCount = commentRepository.countByPost(post);
            postList.add(new PostResponseDto(post, commentCount));
        }
        return postList;
    }


    // 좋아요는 like, 스크랩은 scrap, 최신순 recent
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostsOfCategoryForFilter(String category, int pageNum, String filter) {
        Pageable pageable = PageRequest.of(pageNum, 10);
        List<PostResponseDto> postList = new ArrayList<>();
        Page<Post> posts;

        switch (filter) {
            case "like":
                posts = postRepository.findAllByPostStatusAndCategoryOrderByLikePostSumDesc(pageable, true, category);
                break;
            case "scrap":
                posts = postRepository.findAllByPostStatusAndCategoryOrderByScrapPostSumDesc(pageable, true, category);
                break;
            case "recent":
                posts = postRepository.findAllByPostStatusAndCategoryOrderByCreatedAtDesc(pageable, true, category);
                break;
            default:
                throw new CustomException(ErrorCode.WRONG_POST);
        }

        for (Post post : posts) {
            Long commentCount = commentRepository.countByPost(post);
            postList.add(new PostResponseDto(post, commentCount));
        }
        return postList;
    }

    // 카테고리별 총 게시글 갯수 보내주기
    @Transactional
    public CategoryDto getCountOfCategory() {
        Long meal = postRepository.countByCategoryAndPostStatus("meal", true);
        Long drink = postRepository.countByCategoryAndPostStatus("drink", true);
        Long recycle = postRepository.countByCategoryAndPostStatus("recycle", true);
        CategoryDto categoryDto = new CategoryDto(meal, drink, recycle);
        return categoryDto;
    }

    // 선택 포스트 가져오기
    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다")
        );

        User user = SecurityUtil.getCurrentUser();
        boolean IsLikedPost = false;
        boolean IsScrapPost = false;

        if (user != null) {
            IsLikedPost = likePostRepository.existsByUserIdAndPostId(user.getId(), post.getId());
            IsScrapPost = scrapPostRepository.existsByUserIdAndPostId(user.getId(), post.getId());
        }

        List<CommentResponseDto> commentList = new ArrayList<>();
        for (Comment comment : post.getComments()) {
            commentList.add(new CommentResponseDto(comment));
        }


        return new PostResponseDto(post,commentList, IsLikedPost, IsScrapPost);
    }
    // 선택포스트 스크랩하기
    @Transactional(readOnly = false)
    public ScrapButtonResponseDto scrapPost(User user, Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOTFOUND_POST));
        ScrapPost scrapPost = scrapPostRepository.findByUserAndPost(user, post);
        if (scrapPost == null) {
            scrapPost = new ScrapPost(post, user);
            scrapPostRepository.saveAndFlush(scrapPost);
            post.plusScrapPostSum();
            return new ScrapButtonResponseDto(true, post.getScrapPostSum(), "성공적으로 스크랩 추가하였습니다");
        } else {
            scrapPostRepository.deleteById(scrapPost.getId());
            post.minusScrapPostSum();
            return new ScrapButtonResponseDto(false, post.getScrapPostSum(), "스크랩을 삭제하였습니다");
        }
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



    @Transactional
    public List<PostResponseDto> searchPost(String keyword, int pageNum, String category) {
        Pageable pageable = PageRequest.of(pageNum, 10);
        List<PostResponseDto> postList = new ArrayList<>();
        int searchPostSum = postRepository.countByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword);

        Page<Post> posts;
        if (category.equals("recent")) {
            posts = postRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrderByCreatedAtDesc(pageable, keyword, keyword);
        } else if (category.equals("like")) {
            posts = postRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrderByLikePostSumDesc(pageable, keyword, keyword);
        } else {
            posts = postRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrderByScrapPostSumDesc(pageable, keyword, keyword);
        }

        if (posts.isEmpty()) return postList;

        for(Post post : posts) {
            Long commentCount = commentRepository.countByPost(post);

            String categoryName;
            if (post.getCategory().equals("meal")) {
                categoryName = "혼밥";
            } else if (post.getCategory().equals("drink")) {
                categoryName = "혼술";
            } else {
                categoryName = "리사이꿀";
            }

            postList.add(new PostResponseDto(post, searchPostSum, commentCount, categoryName));
        }
        return postList;
    }


    // 월드컵용 매서드
    LocalDateTime today = LocalDateTime.now();
    String num = today.format(DateTimeFormatter.ofPattern("YYYY.MM"));
    LocalDateTime monthAgo = LocalDateTime.of(LocalDate.now().minusDays(30), LocalTime.of(0, 0, 0));

    // 이미지 월드컵 용 사진 가져오기
    @Transactional
    public List<FoodWorldcupResponseDto> getWorldcupImage() {
        List<FoodWorldcupResponseDto> imageList = new ArrayList<>();
        Pageable pageable = PageRequest.of(0, 16);
        Page<Post> posts = postRepository.findAllByCreatedAtIsAfterAndPostStatusAndCategoryAndImageFileStartingWithOrderByLikePostSumDesc(pageable, monthAgo, true, "meal", "https://ggultong.s3");
        for (Post post : posts) {
            imageList.add(new FoodWorldcupResponseDto(post));
        }
        Collections.shuffle(imageList);
        return imageList;
    }
    // 해당 회차에 월드컵 이미지 순위 5개 넣어주는 코드
    @Transactional
    public List<FoodWorldcupResponseDto> worldcupImageRank(Long id) {
        if(worldCupRepository.existsByPostIdAndNum(id, num)) {
            FoodWorldCup foodWorldCup = worldCupRepository.findByPostId(id);
            foodWorldCup.point();
        } else {
            Post post = postRepository.findById(id).orElseThrow(
                    () -> new CustomException(ErrorCode.NOTFOUND_POST)
            );
            worldCupRepository.saveAndFlush(new FoodWorldCup(num, id, 1, post));
        }
        List<FoodWorldcupResponseDto> topRank = new ArrayList<>();
        Pageable pageable = PageRequest.of(0, 5);
        Page<FoodWorldCup> worldCupRank = worldCupRepository.findAllByNumOrderByPointDesc(pageable, num);
        List<FoodWorldCup> points = worldCupRepository.findByNumOrderByPointDesc(num);
        int pointSum = 0;
        for (FoodWorldCup point : points) {
            pointSum += point.getPoint();
        }
        for (FoodWorldCup worldCup : worldCupRank) {
            double result = (double) worldCup.getPoint() / pointSum;
            double result1 = result * 100;
            int percent = (int) result1;
            topRank.add(new FoodWorldcupResponseDto(worldCup, percent));
        }
        return topRank;
    }
    @Transactional
    public List<FoodWorldcupResponseDto> getWorldcupTop5() {
        List<FoodWorldcupResponseDto> topRank = new ArrayList<>();
        Pageable pageable = PageRequest.of(0, 5);
        Page<FoodWorldCup> worldCupRank = worldCupRepository.findAllByNumOrderByPointDesc(pageable, num);
        int pointSum = 0;
        List<FoodWorldCup> points = worldCupRepository.findByNumOrderByPointDesc(num);
        for (FoodWorldCup point : points) {
            pointSum += point.getPoint();
        }
        for (FoodWorldCup worldCup : worldCupRank) {
            worldCup.point();
            double result = (double) worldCup.getPoint() / pointSum;
            double result1 = result * 100;
            int percent = (int) result1;
            topRank.add(new FoodWorldcupResponseDto(worldCup, percent));
        }

        return topRank;
    }

    // 일년치 월별 우승, 준우승 가져오는 2차원 배열
    @Transactional
    public FoodWorldcupResponseDto[][] getWorldcupMonth() {
        FoodWorldcupResponseDto[][] monthRank = new FoodWorldcupResponseDto[12][2];
        for(int i = 1; i <= 12; i ++) {
            String defalut = today.withYear(2023).withMonth(i).format(DateTimeFormatter.ofPattern("YYYY.MM"));
            List<FoodWorldCup> worldCupRank = worldCupRepository.findTop2ByNumOrderByPointDesc(defalut);

            int j = 0;
            for (FoodWorldCup worldCup : worldCupRank) {
                monthRank[i - 1][j++] = new FoodWorldcupResponseDto(worldCup);
            }

            while (j < 2) {
                monthRank[i-1][j++] = new FoodWorldcupResponseDto();
            }
        }
        return monthRank;
    }
    // 메인페이지 top6
    // 카테고리별 인기순 6개씩 가져오는 페이지
    @Transactional
    public MainPagePostResponseDto[][] getLikeTop6() {
        MainPagePostResponseDto[][] top6 = new MainPagePostResponseDto[3][6];
        Pageable pageable = PageRequest.of(0, 6);
        List<String> categories = Arrays.asList("drink", "meal", "recycle");

        for (int i = 0; i < 3; i++) {
            Page<Post> posts = postRepository.findAllByCreatedAtIsAfterAndPostStatusAndCategoryAndImageFileStartingWithOrderByLikePostSumDesc(pageable, monthAgo, true, categories.get(i), "https://ggultong.s3");
            int j = 0;
            for (Post post : posts) {
                top6[i][j++] = new MainPagePostResponseDto(post);
            }
        }
        return top6;
    }
}
