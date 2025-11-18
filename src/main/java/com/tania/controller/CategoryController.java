package com.tania.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tania.dto.CategoryRequestDto;
import com.tania.dto.CategoryResponseDto;
import com.tania.service.CategoryService;
import com.tania.service.impl.FileService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;
    private final FileService fileService;

    @PostMapping(value = "", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public CategoryResponseDto createCategory(
            @RequestPart("jsonData") String jsonData,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        CategoryRequestDto dto = mapper.readValue(jsonData, CategoryRequestDto.class);
        
        if (imageFile != null && !imageFile.isEmpty()) {
            String image = fileService.uploadImage(imageFile);
            dto.setImage(image);
        }

        return service.createCategory(dto);
    }



    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CategoryResponseDto updateCategory(
            @PathVariable Long id,
            @RequestPart("jsonData") String jsonData,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) throws IOException {

        // Convert JSON string → DTO
        ObjectMapper mapper = new ObjectMapper();
        CategoryRequestDto dto = mapper.readValue(jsonData, CategoryRequestDto.class);

        // If new image present, upload it
        if (imageFile != null && !imageFile.isEmpty()) {
            String image = fileService.uploadImage(imageFile);
            dto.setImage(image);
        }

        return service.updateCategory(id, dto);
    }



    @GetMapping("/{id}")
    public CategoryResponseDto getById(@PathVariable Long id) {
        return service.getCategoryById(id);
    }

    @GetMapping
    public List<CategoryResponseDto> getAll() {
        return service.getAllCategories();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteCategory(id);
        return "Category deleted successfully";
    }
}
