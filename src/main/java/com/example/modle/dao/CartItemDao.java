package com.example.modle.dao;

import com.example.modle.entity.CartItem;

import java.util.List;

public interface CartItemDao {

    List<CartItem> findAll(Integer idCart);
    CartItem findById(Integer id) ;

    Boolean create(CartItem item) ;
    Boolean updateQty(Integer qty , Integer id) ;
    Boolean delete ( Integer id ) ;
}
