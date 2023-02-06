package com.ggt.finalproject.repository;

import com.ggt.finalproject.dto.PostResponseDto;
import com.ggt.finalproject.entity.Post;
import com.ggt.finalproject.entity.User;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

    //
    Page<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrderByModifiedAtDesc(Pageable pageable, String title, String content);
    // title, content로검색하였을때 페이지별로 나타냄

    int countByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content);
    // 검색하였을때 총 갯수를 나타내줌

    Page<Post> findAllByPostStatusAndCategoryOrderByCreatedAtDesc(Pageable pageable, boolean postStatus, String category);
    // 카테고리별로 status가 true인 포스트를 최근생성시간기준으로 페이지별로 가져옴

    Page<Post> findAllByCreatedAtIsAfterAndPostStatusAndCategoryAndImageFileStartingWithOrderByLikePostSumDesc(Pageable pageable, LocalDateTime start, boolean postStatus, String category, String starting);
    // 현재기점 이전 한달간 삭제되지 않은 포스트 중 meal 카테고리의 이미지 있는 게시글 중 좋아요 높은 순 16개 가져옴

    Long countByCategoryAndPostStatus(String category, boolean postStatus);
    //카테고리별 전체 게시글 수를 알려줌

    Long countByUserAndPostStatus(User user, boolean postStatus);
    // status가 true인 해당 유저의 글 갯수를 알려줌
    Page<Post> findAllByUserAndPostStatusOrderByCreatedAtDesc(Pageable pageable, User user, boolean postStatus);
    // status가 true인 해당 유저의 글들 모두를 가져옴
}
