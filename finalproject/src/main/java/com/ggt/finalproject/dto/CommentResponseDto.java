package com.ggt.finalproject.dto;

import com.ggt.finalproject.entity.Comment;

import java.time.LocalDateTime;
import java.util.List;

public class CommentResponseDto {

    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private String nickname;

    private String content;


    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
//        this.createdAt = comment.getCreatedAt();
//        this.modifiedAt = comment.getModifiedAt();
        this.nickname = comment.getNickname();
        this.content = comment.getContent();
    }

    public Long getId(){return this.id;}

    public String getNickname(){return this.nickname;}

    public String getContent(){return this.content;}

}
