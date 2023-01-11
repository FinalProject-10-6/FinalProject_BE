package com.ggt.finalproject.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PostRequestDto {
    private String title;
    private String content;
    private String category;

    public PostRequestDto(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }
}