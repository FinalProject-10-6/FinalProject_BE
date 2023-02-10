package com.ggt.finalproject.dto;

import com.ggt.finalproject.entity.Post;
import lombok.Getter;

@Getter
public class MainPagePostResponseDto {
    private Long postId;
    private String title;
    private String imageUrl;

    public MainPagePostResponseDto(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.imageUrl = post.getImageFile();
    }
}
