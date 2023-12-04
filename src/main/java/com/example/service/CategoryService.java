package com.example.service;

import com.example.dto.request.CategoryDto;
import com.example.dto.response.RespCategoryDto;
import com.example.modle.entity.Category;

import java.util.List;

public interface CategoryService  {
    List<RespCategoryDto> findAll();
    List<Category> getAll();

    Boolean create(CategoryDto t);
    RespCategoryDto findById(Integer id);
    Boolean updateById(Category c, Integer id);
    void deleteById(Integer id);
}
