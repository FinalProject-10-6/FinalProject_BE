package com.ggt.finalproject.dto;

import com.ggt.finalproject.entity.FoodWorldCup;
import com.ggt.finalproject.entity.Post;
import lombok.Getter;

@Getter
public class WorldCupImageDto {
    private Long id;
    private String title;
    private String imageUrl;
    private int point;

    public WorldCupImageDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.imageUrl = post.getImageFile();
    }
    public WorldCupImageDto(FoodWorldCup foodWorldCup) {
        this.id = foodWorldCup.getId();
        this.point = foodWorldCup.getPoint();
    }
}
