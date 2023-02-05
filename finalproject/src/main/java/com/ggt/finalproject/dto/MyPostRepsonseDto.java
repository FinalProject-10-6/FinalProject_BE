package com.ggt.finalproject.dto;

import com.ggt.finalproject.entity.Post;
import com.ggt.finalproject.entity.ScrapPost;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyPostRepsonseDto {

    private Long postId;
    private String category;
    private String title;
    private String nickname;
    private Long likeSum;
    private Long scrapSum;
    private int commentCount;
    private LocalDateTime createdAt;
    private String imageFile;
    private String profileImage;


    public MyPostRepsonseDto(Post post) {
        this.postId = post.getId();
        this.category = post.getCategory();
        this.title = post.getTitle();
        this.nickname = post.getUser().getNickname();
        this.likeSum = post.getLikePostSum();
        this.scrapSum = post.getScrapPostSum();
        this.createdAt = post.getCreatedAt();
        this.imageFile = post.getImageFile();
        this.profileImage = post.getUser().getProfileImg();
        this.commentCount = post.getCommentList().size();
    }

    public MyPostRepsonseDto(ScrapPost scrapPost) {
        this.postId = scrapPost.getPost().getId();
        this.category = scrapPost.getPost().getCategory();
        this.title = scrapPost.getPost().getTitle();
        this.nickname = scrapPost.getPost().getUser().getNickname();
        this.likeSum = scrapPost.getPost().getLikePostSum();
        this.scrapSum = scrapPost.getPost().getScrapPostSum();
        this.createdAt = scrapPost.getPost().getCreatedAt();
        this.imageFile = scrapPost.getPost().getImageFile();
        this.profileImage = scrapPost.getPost().getUser().getProfileImg();
        this.commentCount = scrapPost.getPost().getCommentList().size();
    }
}
