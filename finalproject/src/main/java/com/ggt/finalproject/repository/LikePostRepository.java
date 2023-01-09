package com.ggt.finalproject.repository;

import com.ggt.finalproject.entity.LikePost;
import com.ggt.finalproject.entity.Post;
import com.ggt.finalproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    LikePost findByPostAndUser(Post post, User user);
    Long countByPost(Post post);
}
