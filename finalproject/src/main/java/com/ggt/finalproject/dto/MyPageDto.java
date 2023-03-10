package com.ggt.finalproject.dto;

import com.ggt.finalproject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyPageDto {

//    @NotEmpty(message = "닉네임은 필수입니다.")
    private String nickname;

    private String profileImg;

    private String email;

    private String loginId;


    public MyPageDto(String nickname, String profileImg){
        this.nickname = nickname;
        this.profileImg = profileImg;
    }

    public MyPageDto(User user){
        this.nickname = user.getNickname();
        this.profileImg = user.getProfileImg();

    }


//    public MyPageDto(String nickname, User user){
//        this.nickname = nickname;
//        this.loginId = user.getLoginId();
//        this.password = user.getPassword();
//        this.email = user.getEmail();
//    }


}
