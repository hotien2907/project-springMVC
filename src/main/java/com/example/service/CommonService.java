package com.example.service;

import com.example.dto.request.ProductDto;
import com.example.dto.response.RespProductDto;
import com.example.modle.entity.Product;

import java.util.List;

public interface CommonService {

    List<RespProductDto> productByCategory(Integer categoryId);
    List<RespProductDto>searchProduct(String keyName);
    List<RespProductDto>sortProduct(int value);

    List<RespProductDto> productRelated(int idCategory , int idProduct);
    List<RespProductDto>findAllProductPage(int startNumber, int size);

}
