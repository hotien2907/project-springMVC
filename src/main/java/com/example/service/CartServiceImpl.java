package com.example.service;

import com.example.modle.dao.CartDao;
import com.example.modle.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDao cartDao;
    @Override
    public Boolean addCart(Cart cart) {
        return cartDao.addCart(cart);
    }

    @Override
    public Cart findByIdUser(Integer idUser) {
        return cartDao.findByIdUser(idUser);
    }
}
