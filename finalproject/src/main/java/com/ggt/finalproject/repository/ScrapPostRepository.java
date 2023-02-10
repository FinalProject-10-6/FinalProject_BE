package com.ggt.finalproject.repository;

import com.ggt.finalproject.entity.LikePost;
import com.ggt.finalproject.entity.Post;
import com.ggt.finalproject.entity.ScrapPost;
import com.ggt.finalproject.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScrapPostRepository extends JpaRepository<ScrapPost, Long> {
    boolean existsByUserIdAndPostId(Long userId, Long postId);
    ScrapPost findByUserAndPost(User user, Post post);
    Long countByUser(User user);
    Long countByPost(Post post);
    Page<ScrapPost> findAllByUser(Pageable pageable, User user);
}
