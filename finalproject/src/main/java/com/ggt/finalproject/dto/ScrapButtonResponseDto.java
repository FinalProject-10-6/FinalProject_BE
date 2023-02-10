package com.ggt.finalproject.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
public class ScrapButtonResponseDto {
    private boolean IsScrapPost;
    private Long scrapPostSum;
    private String msg;

    public ScrapButtonResponseDto(boolean isScrapPost, Long scrapPostSum, String msg) {
        IsScrapPost = isScrapPost;
        this.scrapPostSum = scrapPostSum;
        this.msg = msg;
    }
}
