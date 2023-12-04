package com.example.service;

import com.example.dto.request.CategoryDto;
import com.example.dto.response.RespCategoryDto;
import com.example.modle.dao.CategoryDao;

import com.example.modle.entity.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryDao categoryDao;

    @Override
    public List<RespCategoryDto> findAll() {
        List<Category> categories = categoryDao.findAll();
        ModelMapper modelMapper = new ModelMapper();

        return categories.stream()
                .map(category -> modelMapper.map(category, RespCategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Category> getAll() {
        return categoryDao.findAll();
    }


    @Override
    public Boolean create(CategoryDto categoryDto) {
        ModelMapper modelMapper = new ModelMapper();
        Category category = modelMapper.map(categoryDto, Category.class);
        return categoryDao.create(category);
    }

    @Override
    public RespCategoryDto findById(Integer id) {
        Category category = categoryDao.findById(id);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(category, RespCategoryDto.class);
    }

    @Override
    public Boolean updateById(Category c, Integer id) {

        return categoryDao.update(c, id);
    }

    @Override
    public void deleteById(Integer id) {
        categoryDao.delete(id);
    }

}
