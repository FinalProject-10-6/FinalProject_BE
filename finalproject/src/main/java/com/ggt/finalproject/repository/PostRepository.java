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

    List<Post> findAllByPostStatusOrderByCreatedAtDesc(boolean postStatus);
    Page<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrderByModifiedAtDesc(Pageable pageable, String title, String content);
    // 페이지 카테고리별
    Page<Post> findAllByPostStatusAndCategoryOrderByCreatedAtDesc(Pageable pageable, boolean postStatus, String category);
    // 현재기점 이전 한달간 삭제되지 않은 포스트 중 meal 카테고리의 이미지 있는 게시글 중 좋아요 높은 순 16개 가져오기
    Page<Post> findAllByCreatedAtIsAfterAndPostStatusAndCategoryAndImageFileStartingWithOrderByLikePostSumDesc(Pageable pageable, LocalDateTime start, boolean postStatus, String category, String starting);
    Long countByCategoryAndPostStatus(String category, boolean postStatus);

    List<Post> findByUserAndPostStatusOrderByCreatedAtDesc(User user, boolean postStatus);

}
