package com.ggt.finalproject.entity;

import com.ggt.finalproject.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Where(clause = "postStatus = true")
//@SQLDelete(sql = "UPDATE post SET postStatus = false WHERE id = ?")
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

    @ElementCollection
    private List<String> imageFiles;


    public void postStatus() {
        this.postStatus = false;
    }

    public Post(PostRequestDto requestDto, User user,  List<String> imageFiles) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.imageFiles = imageFiles;
        this.category = requestDto.getCategory();
        this.user = user;
        this.likePostSum = 0L;
    }

    // 좋아요를 위해 추가 - 종열
    public void setLikePostSum(Long sum){
        this.likePostSum = sum;
    }
}
