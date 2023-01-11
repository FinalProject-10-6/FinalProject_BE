
package com.ggt.finalproject.repository;

import com.ggt.finalproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findAllByPostId(Long id);

////    void deleteAllByUsername(String nickname);

}
