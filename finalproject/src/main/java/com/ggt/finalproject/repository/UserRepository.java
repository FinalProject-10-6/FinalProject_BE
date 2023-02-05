package com.ggt.finalproject.repository;

import com.ggt.finalproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByLoginIdAndUserStatus(String loginId, boolean status);
    boolean existsByNicknameAndUserStatus(String nickname, boolean status);
    boolean existsByEmailAndUserStatus(String email, boolean status);

    Optional<User> findByLoginIdAndUserStatus(String loginId, boolean status);
    Optional<User> findByNicknameAndUserStatus(String nickname, boolean status);
    Optional<User> findByEmailAndUserStatus(String email, boolean status);
    Optional<User> findByLoginId(String loginId);
    Optional<User> findByEmail(String email);
}
