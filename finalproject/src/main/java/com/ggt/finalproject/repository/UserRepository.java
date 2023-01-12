package com.ggt.finalproject.repository;

import com.ggt.finalproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByLoginId(String loginId);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);

    Optional<User> findByLoginId(String loginId);
    Optional<User> findByNickname(String nickname);
    Optional<User> findByKakaoId(String id);
    Optional<User> findByEmail(String email);
}
