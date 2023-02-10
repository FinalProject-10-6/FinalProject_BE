package com.ggt.finalproject.dto;

import lombok.Getter;

@Getter
public class MypageCountDto {
    private Long myPost;
    private Long myScrap;

    public MypageCountDto(Long myPost, Long myScrap) {
        this.myPost = myPost;
        this.myScrap = myScrap;
    }
}
