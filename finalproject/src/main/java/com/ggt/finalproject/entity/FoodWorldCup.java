package com.ggt.finalproject.entity;

import com.ggt.finalproject.dto.PostRequestDto;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class FoodWorldCup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private int num;
    @Column
    private Long postId;
    @Column
    private int point;

    public void point() {
        this.point += 1;
    }

    public FoodWorldCup(int num, Long postId, int point) {
        this.num = num;
        this.postId = postId;
        this.point = point;
    }
}
