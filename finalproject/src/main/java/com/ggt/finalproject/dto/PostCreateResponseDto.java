package com.ggt.finalproject.dto;


import com.ggt.finalproject.entity.Post;
import com.ggt.finalproject.entity.User;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
public class PostCreateResponseDto {

    private Long id;
    private String nickname;
    private String content;
    private String title;
    private String imageFile;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;
    private String category;
    private String msg;
    private int statusCode;

    public PostCreateResponseDto(Post post, User user, String msg) {
        this.id = post.getId();
        this.nickname = user.getNickname();
        this.content = post.getContent();
        this.title = post.getTitle();
        this.imageFile = post.getImageFile();
        this.modifiedAt = post.getModifiedAt();
        this.createdAt = post.getCreatedAt();
        this.category = post.getCategory();
        this.msg = msg;
        this.statusCode = HttpStatus.OK.value();
    }
}
