package com.ggt.finalproject.entity;


import com.ggt.finalproject.dto.MyPageDto;
import com.ggt.finalproject.dto.SocialSetResponseDto;
import com.ggt.finalproject.security.UserDetailsImpl;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "USERS")      //  java 에서 user 는 reserved keyword 이기때문에 테이블생성 X  따른이름을 부여함
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String loginId;
    private Long kakaoId;


    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String profileImg = "https://ggultong.s3.ap-northeast-2.amazonaws.com/defalut.png";

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum userRole;

    @Column(nullable = false)
    private boolean userStatus = true;           // true = 정상회원   false = 탈퇴회원

    @Column
    private String category;



    public User(String loginId, String password, String email, String nickname) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.userRole = UserRoleEnum.USER;
    }

    public User(String loginId, String password, String email, String nickname, String profileImg) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.userRole = UserRoleEnum.USER;
        this.profileImg = profileImg;
    }

//    public User(String loginId, Long kakaoId, String password, String email, String nickname, String profileImg, UserRoleEnum userRole) {
//        this.loginId = loginId;
//        this.kakaoId = kakaoId;
//        this.password = password;
//        this.email = email;
//        this.nickname = nickname;
//        this.profileImg = profileImg;
//        this.userRole = userRole;
//    }

    public User(String loginId, String password, String email, UserRoleEnum userRole) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.userRole = userRole;
    }


    public void updateMyPage(MyPageDto myPageDto){
        this.nickname = myPageDto.getNickname();
        this.profileImg = myPageDto.getProfileImg();
    }


    public void changePassword(String password){
        this.password = password;
    }



    public User kakaoIdUpdate(String loginId){
        this.loginId = loginId;
        return this;
    }
    public User(Long id) {
        this.id = getId();
    }

    public String getNickname(){
        return this.nickname;
    }



    public User naverIdUpdate(String loginId) {
        this.loginId = loginId;
        return this;
    }

    public void softDelete(){
        this.userStatus = false;
    }


    public void socialUpdate(String nickname){
        this.nickname = nickname;
    }


    public void updatePw(String nPw){
        this.password = nPw;
    }


}
