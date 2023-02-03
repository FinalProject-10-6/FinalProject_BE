package com.ggt.finalproject.dto;

import com.ggt.finalproject.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyPostRepsonseDto {

    private String title;
    private String nickname;
    private Long likeSum;
    private Long scrapSum;
    private LocalDateTime createdAt;
    private String imageFile;
    private String profileImage;


    public MyPostRepsonseDto(Post post) {
        this.title = post.getTitle();
        this.nickname = post.getUser().getNickname();
        this.likeSum = post.getLikePostSum();
        this.scrapSum = post.getScrapPostSum();
        this.createdAt = post.getCreatedAt();
        this.imageFile = post.getImageFile();
        this.profileImage = post.getUser().getProfileImg();
    }
}
