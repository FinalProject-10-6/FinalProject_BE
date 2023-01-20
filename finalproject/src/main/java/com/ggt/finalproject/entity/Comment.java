package com.ggt.finalproject.entity;

import com.ggt.finalproject.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nickname;

    @Column
    private String content;

    @Column
    private String profileImg;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public void updatePost(Post post){
        this.post = post;
    }

    public Comment(CommentRequestDto requestDto, User user) {
        this.nickname = user.getNickname();
        this.profileImg = user.getProfileImg();
        this.content = requestDto.getContent();
    }

    public void update(CommentRequestDto requestDto) {
        this.content =  requestDto.getContent();
    }





}