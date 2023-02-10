
package com.ggt.finalproject.repository;

import com.ggt.finalproject.entity.Comment;
import com.ggt.finalproject.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findAllByPostId(Long id);

    List<Comment> findAllByOrderByCreatedAtDesc(Pageable pageable);

    // 코멘트 갯수 세기 위한 코드 -> 상정
    Long countByPost(Post post);

////    void deleteAllByUsername(String nickname);

}
