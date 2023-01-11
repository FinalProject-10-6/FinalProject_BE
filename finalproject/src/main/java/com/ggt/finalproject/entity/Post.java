package com.ggt.finalproject.entity;

import com.ggt.finalproject.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Where(clause = "postStatus = true")
//@SQLDelete(sql = "UPDATE post SET postStatus = false WHERE id = ?")
@Entity
@NoArgsConstructor
@Getter
public class Post extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String content;
    @Column
    private String category;
//    @Column
//    private String videoFile;
    @Column
    private String imageFile;
    @Column(nullable = false)
    private boolean postStatus = true;    // true = 정상게시글   false = 삭제된글

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    public void postStatus() {
        this.postStatus = false;
    }

    public Post(PostRequestDto requestDto, User user, String imageFile) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.imageFile = imageFile;
        this.category = requestDto.getCategory();
        this.user = user;
    }

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    public void addComment(Comment comment){
        this.commentList.add(comment);
        comment.updatePost(this);
    }


}
