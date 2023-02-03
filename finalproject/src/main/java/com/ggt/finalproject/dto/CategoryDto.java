package com.ggt.finalproject.dto;

import lombok.Getter;

@Getter
public class CategoryDto {


    private Long meal;
    private Long drink;
    private Long recycle;

    public CategoryDto(Long meal, Long drink, Long recycle) {
        this.meal = meal;
        this.drink = drink;
        this.recycle = recycle;
    }
}
