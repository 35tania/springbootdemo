package com.tania.service;

import com.tania.dto.CategoryRequestDto;
import com.tania.dto.CategoryResponseDto;
import java.util.List;

public interface CategoryService {

    CategoryResponseDto createCategory(CategoryRequestDto dto);

    CategoryResponseDto updateCategory(Long id, CategoryRequestDto dto);

    CategoryResponseDto getCategoryById(Long id);

    List<CategoryResponseDto> getAllCategories();

    void deleteCategory(Long id);
}
