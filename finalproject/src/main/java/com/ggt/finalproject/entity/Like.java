//package com.ggt.finalproject.entity;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Entity
//@Getter
//@NoArgsConstructor
//public class Like {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "post_id", nullable = false)
//    private Post post;
//
//
//    public Like(User user, Post post) {
//        this.user = user;
//        this.post = post;
//    }
//}
