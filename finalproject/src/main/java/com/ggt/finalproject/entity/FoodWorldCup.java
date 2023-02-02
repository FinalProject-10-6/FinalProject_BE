package com.ggt.finalproject.entity;

import com.ggt.finalproject.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class FoodWorldCup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String num;
    @Column
    private Long postId;
    @Column
    private int point;
    @ManyToOne
    @JoinColumn(name = "post_originalId",nullable = false)
    private Post post;

    public void point() {
        this.point += 1;
    }

    public FoodWorldCup(String num, Long postId, int point, Post post) {
        this.num = num;
        this.postId = postId;
        this.point = point;
        this.post = post;
    }
}
