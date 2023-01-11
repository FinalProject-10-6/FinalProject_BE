package com.ggt.finalproject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class EmailCode {

    private static final Long MAX_EXPIRE_TIME = 5L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String emailCode;

    public EmailCode(String email, String emailCode) {
        this.email = email;
        this.emailCode = emailCode;
    }


    public void update(String email, String ePw) {
        this.email = email;
        this.emailCode = ePw;
    }
}
