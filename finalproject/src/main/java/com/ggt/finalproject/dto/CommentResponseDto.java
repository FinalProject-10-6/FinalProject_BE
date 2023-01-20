package com.ggt.finalproject.dto;

import com.ggt.finalproject.entity.Comment;
import com.ggt.finalproject.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CommentResponseDto {

    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private String nickname;

    private String content;

    private String profileImg;


    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.nickname = comment.getNickname();
        this.content = comment.getContent();
        this.profileImg = comment.getProfileImg();
    }
}
