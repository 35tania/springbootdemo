package com.tania.dto;

import lombok.Data;

@Data
public class CategoryRequestDto {

    private String name;
    private String categoryId;
    private Long parentCategoryId; 
    private Integer level;
    private String image; 
}
