package com.ggt.finalproject.dto;

import com.ggt.finalproject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SocialSetResponseDto {

    private String nickname;

    private String loginId;

    private String email;

    public SocialSetResponseDto (String nickname, User user){
        this.nickname = nickname;
        this.loginId = user.getLoginId();
        this.email = user.getEmail();
    }

}
