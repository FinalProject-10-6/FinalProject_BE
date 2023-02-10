package com.ggt.finalproject.dto;

import com.ggt.finalproject.entity.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserInfoDto {
    private Long id;
    private String email;
    private String nickname;
    private UserRoleEnum userRole;
    private String profileImg;

    public KakaoUserInfoDto(Long id, String nickname, String email, String profileImg) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.profileImg = profileImg;
    }
}
