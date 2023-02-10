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
    private String content;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void updatePost(Post post){
        this.post = post;
    }

    public Comment(CommentRequestDto requestDto, User user) {
        this.content = requestDto.getContent();
        this.user = user;
    }

    public void update(CommentRequestDto requestDto) {
        this.content =  requestDto.getContent();
    }





}