package com.example.modle.dao;

import com.example.modle.entity.Product;

import java.util.List;

public interface CommonDao {
  List <Product>  productByCategory(Integer categoryId);
 List <Product> searchProduct(String keyName);

 List<Product> sortProduct (int value);
 List<Product> productRelated(int idCategory , int idProduct);
}
