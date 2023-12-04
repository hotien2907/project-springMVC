package com.example.modle.dao;

import com.example.modle.entity.Cart;

public interface CartDao {
    Boolean addCart(Cart cart) ;
    Cart findByIdUser( Integer idUser) ;

    public boolean clearCart(int cartId) ;
}
