package com.ggt.finalproject.dto;

import com.ggt.finalproject.entity.FoodWorldCup;
import com.ggt.finalproject.entity.Post;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FoodWorldcupResponseDto {
    private Long id;
    private String title;
    private String imageUrl;
    private int point;

    public FoodWorldcupResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.imageUrl = post.getImageFile();
    }
    public FoodWorldcupResponseDto(FoodWorldCup foodWorldCup) {
        this.id = foodWorldCup.getPostId();
        this.title = foodWorldCup.getPost().getTitle();
        this.imageUrl = foodWorldCup.getPost().getImageFile();
        this.point = foodWorldCup.getPoint();
    }
}
