package com.example.service;

import com.example.modle.entity.Cart;


public interface CartService {
    Boolean addCart(Cart cart) ;
    Cart findByIdUser( Integer id) ;
}
