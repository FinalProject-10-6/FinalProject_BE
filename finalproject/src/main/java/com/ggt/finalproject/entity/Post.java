package com.ggt.finalproject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Where(clause = "postStatus = true")
@SQLDelete(sql = "UPDATE post SET postStatus = false WHERE id = ?")
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
    @Column
    private String videoFile;
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

}
