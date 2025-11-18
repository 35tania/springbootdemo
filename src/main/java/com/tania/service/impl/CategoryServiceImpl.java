package com.tania.service.impl;

import com.tania.dto.CategoryRequestDto;
import com.tania.dto.CategoryResponseDto;
import com.tania.model.Category;
import com.tania.repository.CategoryRepository;
import com.tania.service.CategoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repo;

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto dto) {

        if (repo.existsByCategoryId(dto.getCategoryId())) {
            throw new RuntimeException("Category ID already exists");
        }

        Category category = new Category();
        category.setName(dto.getName());
        category.setCategoryId(dto.getCategoryId());
        category.setLevel(dto.getLevel());
        category.setImage(dto.getImage());

        if (dto.getParentCategoryId() != null) {
            Category parent = repo.findById(dto.getParentCategoryId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));
            category.setParentCategory(parent);
        }

        repo.save(category);
        return mapToResponse(category);
    }

    @Override
    public CategoryResponseDto updateCategory(Long id, CategoryRequestDto dto) {
        Category category = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(dto.getName());
        category.setLevel(dto.getLevel());
        category.setImage(dto.getImage());

        if (dto.getParentCategoryId() != null) {
            Category parent = repo.findById(dto.getParentCategoryId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));
            category.setParentCategory(parent);
        }

        repo.save(category);
        return mapToResponse(category);
    }

    @Override
    public CategoryResponseDto getCategoryById(Long id) {
        Category category = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        return mapToResponse(category);
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        return repo.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deleteCategory(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Category not found");
        }
        repo.deleteById(id);
    }

    private CategoryResponseDto mapToResponse(Category c) {
        CategoryResponseDto dto = new CategoryResponseDto();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setCategoryId(c.getCategoryId());
        dto.setLevel(c.getLevel());
        dto.setImage(c.getImage());

        if (c.getParentCategory() != null) {
            dto.setParentCategoryId(c.getParentCategory().getId());
            dto.setParentCategoryName(c.getParentCategory().getName());
        }

        return dto;
    }
}
