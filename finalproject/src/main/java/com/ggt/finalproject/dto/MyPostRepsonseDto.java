package com.ggt.finalproject.dto;

import com.ggt.finalproject.entity.Post;
import com.ggt.finalproject.entity.ScrapPost;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyPostRepsonseDto {

    private Long postId;
    private String title;
    private String nickname;
    private Long likeSum;
    private Long scrapSum;
    private LocalDateTime createdAt;
    private String imageFile;
    private String profileImage;


    public MyPostRepsonseDto(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.nickname = post.getUser().getNickname();
        this.likeSum = post.getLikePostSum();
        this.scrapSum = post.getScrapPostSum();
        this.createdAt = post.getCreatedAt();
        this.imageFile = post.getImageFile();
        this.profileImage = post.getUser().getProfileImg();
    }

    public MyPostRepsonseDto(ScrapPost scrapPost) {
        this.postId = scrapPost.getPost().getId();
        this.title = scrapPost.getPost().getTitle();
        this.nickname = scrapPost.getUser().getNickname();
        this.likeSum = scrapPost.getPost().getLikePostSum();
        this.scrapSum = scrapPost.getPost().getScrapPostSum();
        this.createdAt = scrapPost.getPost().getCreatedAt();
        this.imageFile = scrapPost.getPost().getImageFile();
        this.profileImage = scrapPost.getUser().getProfileImg();
    }
}
