package com.ggt.finalproject.dto;

import com.ggt.finalproject.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponseDto {
    private Long num;      // id 가 아니라 num 이라 한 이유가????- 종열
    private String nickname;

    private String content;
    private String title;

//    private String videoFile;

    private List<String> imageFiles;

    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;

    private String category;

    private Long likePostSum;    //  좋아요를 위해 추가 - 종열
    private boolean IsLikedPost;
    public PostResponseDto(Post post) {
        this.num = post.getId();
        this.nickname = post.getUser().getNickname();
        this.content = post.getContent();
        this.title = post.getTitle();
        this.category = post.getCategory();
//        this.videoFile = post.getVideoFile();
        this.imageFiles = post.getImageFiles();
        this.modifiedAt = post.getModifiedAt();
        this.createdAt = post.getCreatedAt();
        this.likePostSum = post.getLikePostSum();  // 좋아요를위해 추가 - 종열
    }

    public PostResponseDto(Post post, boolean IsLikedPost) {
        this.num = post.getId();
        this.nickname = post.getUser().getNickname();
        this.content = post.getContent();
        this.title = post.getTitle();
//        this.videoFile = post.getVideoFile();
        this.imageFile = post.getImageFile();
        this.modifiedAt = post.getModifiedAt();
        this.createdAt = post.getCreatedAt();
        this.likePostSum = post.getLikePostSum();  // 좋아요를위해 추가 - 종열
        this.IsLikedPost = IsLikedPost;

    }
}