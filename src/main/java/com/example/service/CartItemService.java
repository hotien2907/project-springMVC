package com.example.service;

import com.example.modle.entity.CartItem;

import java.util.List;

public interface CartItemService {
    List<CartItem> findAllByIdCart();
    CartItem findById(Integer idProduct) ;

    Boolean create(CartItem item) ;
    Boolean updateQty(Integer qty , Integer id) ;
    Boolean delete ( Integer id ) ;
}
