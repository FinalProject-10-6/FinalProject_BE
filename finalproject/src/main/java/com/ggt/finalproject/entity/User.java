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

    @Column(nullable = false, unique = true)
    private String loginId;
    private Long kakaoId;


    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String profileImg = "/*기본프로필url*/";        // 추후에 기본프로필로 교체

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
        this.password = myPageDto.getPassword();
        this.nickname = myPageDto.getNickname();
        this.profileImg = myPageDto.getProfileImg();
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


    public void softDelete(){
        this.userStatus = false;
    }

    (String nickname, String email, UserDetailsImpl userDetails) {
        this.email = email;
        this.loginId = userDetails.getLoginId();

    public void socialUpdate(SocialSetResponseDto socialSetResponseDto){
        this.nickname = socialSetResponseDto.getNickname();
        this.loginId = socialSetResponseDto.getLoginId();
        this.email = socialSetResponseDto.getEmail();
    }

    public void updatePw(String nPw){
        this.password = nPw;

    }

}