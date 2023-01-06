package com.ggt.finalproject.entity;


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


    public User(String loginId, String password, String email, String nickname) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.userRole = UserRoleEnum.USER;
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

    public User(String loginId, Long kakaoId, String password, String email, UserRoleEnum userRole) {
        this.loginId = loginId;
        this.kakaoId = kakaoId;
        this.password = password;
        this.email = email;
        this.userRole = userRole;
    }

    public User kakaoIdUpdate(Long kakaoId){
        this.kakaoId = kakaoId;
        return this;
    }
}
