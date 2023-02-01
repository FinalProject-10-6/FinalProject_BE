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

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

    List<Post> findAllByPostStatusOrderByCreatedAtDesc(boolean postStatus);
    List<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content);
    // 페이지 카테고리별
    Page<Post> findAllByPostStatusAndCategoryOrderByCreatedAtDesc(Pageable pageable, boolean postStatus, String category);
    Page<Post> findAllByPostStatusAndCategoryAndImageFileStartingWithOrderByLikePostSumDesc(Pageable pageable, boolean postStatus, String category, String starting);

}
