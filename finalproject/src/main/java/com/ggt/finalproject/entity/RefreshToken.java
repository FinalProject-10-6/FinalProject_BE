package com.ggt.finalproject.entity;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
@Getter
@Entity
@NoArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refreshId;
    @NotBlank
    private String refreshToken;
    @NotBlank
    private String loginId;

    public RefreshToken updateToken(String token) {
        this.refreshToken = token;
        return this;
    }

    @Builder
    public RefreshToken(String refreshToken, String loginId) {
        this.refreshToken = refreshToken;
        this.loginId = loginId;
    }
}
