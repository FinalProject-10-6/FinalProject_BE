package com.ggt.finalproject.repository;

import com.ggt.finalproject.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByLoginId(String loginId);

}
