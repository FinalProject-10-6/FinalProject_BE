package com.ggt.finalproject.dto;

import com.ggt.finalproject.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponseDto {
    private Long id;      // id 가 아니라 num 이라 한 이유가????- 종열
    private String nickname;

    private String content;
    private String title;

//    private String videoFile;

    private String imageFile;

    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;

    private String category;

    private Long likePostSum;    //  좋아요를 위해 추가 - 종열
    private boolean IsLikedPost;

    private List<CommentResponseDto> comment = new ArrayList<>();

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.nickname = post.getUser().getNickname();
        this.content = post.getContent();
        this.title = post.getTitle();
        this.category = post.getCategory();
//        this.videoFile = post.getVideoFile();
        this.imageFile = post.getImageFile();
        this.modifiedAt = post.getModifiedAt();
        this.createdAt = post.getCreatedAt();
        this.likePostSum = post.getLikePostSum();  // 좋아요를위해 추가 - 종열
        this.comment = post.getCommentList().stream()
                .map(CommentResponseDto::new).collect(Collectors.toList());
    }

    public PostResponseDto(Post post, boolean IsLikedPost) {
        this.id = post.getId();
        this.nickname = post.getUser().getNickname();
        this.content = post.getContent();
        this.title = post.getTitle();
        this.category = post.getCategory();
//        this.videoFile = post.getVideoFile();
        this.imageFile = post.getImageFile();
        this.modifiedAt = post.getModifiedAt();
        this.createdAt = post.getCreatedAt();
        this.likePostSum = post.getLikePostSum();  // 좋아요를위해 추가 - 종열
        this.IsLikedPost = IsLikedPost;
        this.comment = post.getCommentList().stream()
                .map(CommentResponseDto::new).collect(Collectors.toList());

    }
}