package com.ggt.finalproject.repository;

import com.ggt.finalproject.entity.EmailCode;
import com.ggt.finalproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailCodeRepository extends JpaRepository<EmailCode, Long> {

    Boolean existsByEmail(String email);
    EmailCode findByEmail(String email);

}
