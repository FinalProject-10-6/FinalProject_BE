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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final AWSS3Service awss3Service;
    private final LikePostRepository likePostRepository;
    private final WorldCupRepository worldCupRepository;

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

    // 카테고리별 포스트 가져오기
    // 전체 포스트 가져오기
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostsOfCategory(String category, int pageNum) {
        System.out.println(category);
        Pageable pageable = PageRequest.of(pageNum, 10);
        List<PostResponseDto> postList = new ArrayList<>();
        Page<Post> posts = postRepository.findAllByPostStatusAndCategoryOrderByCreatedAtDesc(pageable, true, category);
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

        User user = SecurityUtil.getCurrentUser();
        boolean IsLikedPost = false;

        if (user != null) {
            IsLikedPost = likePostRepository.existsByUserIdAndPostId(user.getId(), post.getId());
        }

        return new PostResponseDto(post, IsLikedPost);
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
    public List<PostResponseDto> searchPost(String keyword) {
        List<PostResponseDto> postList = new ArrayList<>();
        List<Post> posts = postRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword);
        if (posts.isEmpty()) return postList;

        for(Post post : posts) {
            postList.add(new PostResponseDto(post));
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
        Page<Post> posts = postRepository.findAllByCreatedAtBetweenAndPostStatusAndCategoryAndImageFileStartingWithOrderByLikePostSumDesc(pageable, monthAgo, today, true, "meal", "https://ggultong.s3.ap-northeast-2.amazonaws.com/");
        for (Post post : posts) {
            imageList.add(new FoodWorldcupResponseDto(post));
        }
        Collections.shuffle(imageList);
        return imageList;
    }
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
    @Transactional
    public FoodWorldcupResponseDto[][] getWorldcupMonth() {
        FoodWorldcupResponseDto[][] monthRank = new FoodWorldcupResponseDto[12][2];
        Pageable pageable = PageRequest.of(0, 2);
        for(int i = 1; i <= 12; i ++) {
            List<FoodWorldcupResponseDto> topRank = new ArrayList<>();
            String num = today.minusMonths(i - 1).format(DateTimeFormatter.ofPattern("YYYY.MM"));
            Page<FoodWorldCup> worldCupRank = worldCupRepository.findAllByNumOrderByPointDesc(pageable, num);
            System.out.println(num);
            if(worldCupRepository.existsByNum(num)) {
                for (FoodWorldCup worldCup : worldCupRank) {
                    topRank.add(new FoodWorldcupResponseDto(worldCup));
                }
                if(topRank.size() == 2) {
                    for (int j = 0; j < 2; j++) {
                        monthRank[i - 1][j] = topRank.get(j);
                    }
                } else {
                    monthRank[i-1][0] = topRank.get(0);
                }
            } else {
                break;
            }
            topRank.clear();
        }
        return monthRank;
    }
}
