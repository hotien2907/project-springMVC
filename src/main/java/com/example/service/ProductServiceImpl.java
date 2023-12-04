package com.example.service;

import com.example.dto.request.ProductDto;
import com.example.dto.response.RespProductDto;
import com.example.modle.dao.CategoryDao;
import com.example.modle.dao.ProductDao;
import com.example.modle.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final static ModelMapper modelMapper = new ModelMapper();
    @Autowired
    ProductDao productDao;
    @Autowired
    CategoryDao categoryDao;

    @Override
    public Boolean create(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        return productDao.create(product);
    }

    @Override
    public List<RespProductDto> findAll() {
        List<Product> products = productDao.findAll();

        return products.stream()
                .map(pr -> modelMapper.map(pr, RespProductDto.class))
                .collect(Collectors.toList());
    }
    @Override
    public void delete(int id) {
        productDao.delete(id);
    }


    @Override
    public Boolean update(ProductDto productDto, Integer id) {
        Product product = modelMapper.map(productDto, Product.class);
        return productDao.update(product, id);
    }

    @Override
    public RespProductDto findById(Integer id) {
        Product product = productDao.findById(id);
        return modelMapper.map(product, RespProductDto.class);
    }

    @Override
    public List<RespProductDto> getProductFeatured() {
        List<Product> products = productDao.getProductFeatured();

        return products.stream()
                .map(pr -> modelMapper.map(pr, RespProductDto.class))
                .collect(Collectors.toList());
    }
}
