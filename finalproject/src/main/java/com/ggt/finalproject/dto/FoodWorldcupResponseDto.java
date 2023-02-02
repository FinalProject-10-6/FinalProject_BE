package com.ggt.finalproject.dto;

import com.ggt.finalproject.entity.FoodWorldCup;
import com.ggt.finalproject.entity.Post;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class FoodWorldcupResponseDto {
    private Long id;
    private String title;
    private String imageUrl;
    private int point;
    private String month;
    private int percent;

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
        this.month = month(foodWorldCup);
    }
    public FoodWorldcupResponseDto(FoodWorldCup foodWorldCup, int percent) {
        this.id = foodWorldCup.getPostId();
        this.title = foodWorldCup.getPost().getTitle();
        this.imageUrl = foodWorldCup.getPost().getImageFile();
        this.point = foodWorldCup.getPoint();
        this.month = month(foodWorldCup);
        this.percent = percent;
    }
    public String month(FoodWorldCup foodWorldCup) {
        String date = foodWorldCup.getNum();
        if(String.valueOf(date.charAt(5)).equals("0")) {
            return String.valueOf(date.charAt(6));
        } else {
            return String.valueOf(date.charAt(5)) + String.valueOf(date.charAt(6));
        }
    }
}
