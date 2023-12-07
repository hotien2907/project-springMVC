package com.example.modle.dao;

import com.example.modle.entity.Product;

import java.util.List;

public interface ProductDao extends IGenericDao<Product,Integer> {
    List<Product> getProductFeatured();


}
