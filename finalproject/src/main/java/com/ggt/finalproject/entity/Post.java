package com.ggt.finalproject.entity;

import com.ggt.finalproject.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Post extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String content;
    @Column
    private String category;
//    @Column
//    private String videoFile;
    @Column(nullable = false)
    private boolean postStatus = true;    // true = 정상게시글   false = 삭제된글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
    //좋아요를 위해 추가 - 종열
    @Column
    private Long likePostSum;
    @Column
    private boolean IsLikedPost;    //좋아요를 위해 추가 - 종열
    @Column
    private Long scrapPostSum;
    @Column
    private boolean IsScrapPost;    //좋아요를 위해 추가 - 종열
    @Column
    private String imageFile = "";
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    public void addComment(Comment comment){
        this.commentList.add(comment);
        comment.updatePost(this);
    }

    // 파일 수정 용도
    public void update(PostRequestDto requestDto, String imageFile) {
        this.imageFile = imageFile;
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.category = requestDto.getCategory();
    }
    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.category = requestDto.getCategory();
    }

    // 좋아요를 위해 추가 - 종열
    public void setLikePostSum(Long sum){
        this.likePostSum = sum;
    }

    // 소프트 딜리트용도
    public void soft_delete() {
        this.postStatus = false;
    }

    public Post(PostRequestDto requestDto, User user,  String imageFile) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.imageFile = imageFile ;
        this.category = requestDto.getCategory();
        this.user = user;
        this.likePostSum = 0L;    //좋아요를 위해 추가 - 종열
        this.IsLikedPost = false;
    }

    public Post(PostRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.category = requestDto.getCategory();
        this.user = user;
        this.likePostSum = 0L;    //좋아요를 위해 추가 - 종열
        this.IsLikedPost = false;
    }

}
