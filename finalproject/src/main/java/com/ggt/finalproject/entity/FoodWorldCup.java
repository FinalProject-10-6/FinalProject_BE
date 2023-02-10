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

    // ManyToOne으로 Post를 JoinColumn해오는데 Post쪽에서는 사용할 일이 없어 여기에만 작성함
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
