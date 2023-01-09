package com.ggt.finalproject.dto;

import com.ggt.finalproject.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private Long num;
    private String nickname;

    private String content;
    private String title;

//    private String videoFile;

    private String imageFile;

    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;

    public PostResponseDto(Post post) {
        this.num = post.getId();
        this.nickname = post.getUser().getNickname();
        this.content = post.getContent();
        this.title = post.getTitle();
//        this.videoFile = post.getVideoFile();
        this.imageFile = post.getImageFile();
        this.modifiedAt = post.getModifiedAt();
        this.createdAt = post.getCreatedAt();
    }
}