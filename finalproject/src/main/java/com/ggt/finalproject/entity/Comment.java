package com.ggt.finalproject.entity;

import com.ggt.finalproject.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nickname;

    @Column
    private String content;

    @Column
    private String profileIMG;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public void updatePost(Post post){
        this.post = post;
    }

    public Comment(CommentRequestDto requestDto, String nickname) {
        this.nickname = nickname;
        this.content = requestDto.getContent();
    }

    public void update(CommentRequestDto requestDto) {
        this.content =  requestDto.getContent();
    }





}