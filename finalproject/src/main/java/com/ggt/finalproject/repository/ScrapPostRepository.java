package com.ggt.finalproject.repository;

import com.ggt.finalproject.entity.LikePost;
import com.ggt.finalproject.entity.Post;
import com.ggt.finalproject.entity.ScrapPost;
import com.ggt.finalproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapPostRepository extends JpaRepository<ScrapPost, Long> {
    boolean existsByUserIdAndPostId(Long userId, Long postId);
    ScrapPost findByUserAndPost(User user, Post post);
}
