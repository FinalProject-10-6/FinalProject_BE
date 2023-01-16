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

    @NotEmpty(message = "닉네임은 필수입니다.")
    private String nickname;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    private String password;

    private String profileImg;
    private MultipartFile file;


    public MyPageDto(String nickname, String password){
        this.nickname = nickname;
        this.password = password;
    }


    public MyPageDto(User user){
        this.nickname = user.getNickname();
        this.password = user.getPassword();
        this.profileImg = user.getProfileImg();
    }


//    public static MyPageDto success(String msg, String nickname, String profileImg, String password){
//        return new MyPageDto (msg, HttpStatus.OK.value(), nickname, profileImg, password);
//    }

}
