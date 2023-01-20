package com.ggt.finalproject.dto;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class NaverUserInfoDto {

    private Long id;
    private String nickname;
    private String email;
    private String profileImg;

    public NaverUserInfoDto(Long id, String nickname, String email, String profileImg) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.profileImg = profileImg;
    }


}
