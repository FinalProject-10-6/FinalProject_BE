package com.ggt.finalproject.dto;

import com.ggt.finalproject.entity.FoodWorldCup;
import com.ggt.finalproject.entity.Post;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
public class FoodWorldcupResponseDto {
    private Long id;
    private String title;
    private String imageUrl;
    private int point;
    private String month;
    private int percent;
    private String category;

    public FoodWorldcupResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.imageUrl = post.getImageFile();
        this.category = post.getCategory();
    }
    public FoodWorldcupResponseDto(FoodWorldCup foodWorldCup) {
        this.id = foodWorldCup.getPostId();
        this.title = foodWorldCup.getPost().getTitle();
        this.imageUrl = foodWorldCup.getPost().getImageFile();
        this.point = foodWorldCup.getPoint();
        this.month = month(foodWorldCup);
        this.category = foodWorldCup.getPost().getCategory();
    }
    public FoodWorldcupResponseDto(FoodWorldCup foodWorldCup, int percent) {
        this.id = foodWorldCup.getPostId();
        this.title = foodWorldCup.getPost().getTitle();
        this.imageUrl = foodWorldCup.getPost().getImageFile();
        this.point = foodWorldCup.getPoint();
        this.month = month(foodWorldCup);
        this.percent = percent;
        this.category = foodWorldCup.getPost().getCategory();
    }
    public FoodWorldcupResponseDto() {
        this.id = 0L;
        this.title = "기본값";
        this.imageUrl = "https://ggultong.s3.ap-northeast-2.amazonaws.com/defalutWorldcup.jpg";
        this.point = 0;
        this.month = "기본값";
        this.percent = 0;
        this.category = "기본값";
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
