package com.ggt.finalproject.dto;

import com.ggt.finalproject.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class MyPageDto {

    @NotEmpty(message = "닉네임은 필수입니다.")
    private String nickname;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    private String password;

    private String email;

    private String profileImg;
    private MultipartFile file;


    public MyPageDto(User user){
        this.nickname = user.getNickname();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.profileImg = user.getProfileImg();
    }


}
