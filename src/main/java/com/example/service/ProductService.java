package com.example.service;
import com.example.dto.request.ProductDto;
import com.example.dto.response.RespCategoryDto;
import com.example.dto.response.RespProductDto;
import com.example.modle.entity.Product;

import java.util.List;

public interface ProductService {

    Boolean create(ProductDto pr);
    List<RespProductDto> findAll();

    void delete(int id);
    Boolean update(ProductDto pr,Integer id);

    RespProductDto findById(Integer id);
    List<RespProductDto> getProductFeatured();
}
