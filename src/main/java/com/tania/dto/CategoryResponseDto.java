package com.tania.dto;

import lombok.Data;

@Data
public class CategoryResponseDto {

    private Long id;
    private String name;
    private String categoryId;
    private Integer level;
    private String image;
    private Long parentCategoryId;
    private String parentCategoryName;
}
