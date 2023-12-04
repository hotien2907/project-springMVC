package com.example.service;

import com.example.dto.response.RespUser;
import com.example.modle.dao.CartItemDao;
import com.example.modle.entity.Cart;
import com.example.modle.entity.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
@Service
public class CartItemServiceImpl implements  CartItemService{

    @Autowired
    private CartItemDao cartItemDao;
    @Autowired
    private HttpSession httpSession ;
    @Autowired
    private CartService cartService ;
    @Override
    public List<CartItem> findAllByIdCart() {
        RespUser userLogin = (RespUser) httpSession.getAttribute("user") ;
        Cart cart = cartService.findByIdUser(userLogin.getUserId()) ;
        return cartItemDao.findAll(cart.getCartId());
    }

    @Override
    public CartItem findById(Integer idProduct) {

        return cartItemDao.findById(idProduct);
    }

    @Override
    public Boolean create(CartItem item) {
        RespUser userLogin = (RespUser) httpSession.getAttribute("user");
        Cart cart = cartService.findByIdUser(userLogin.getUserId()) ;
        item.setCart(cart);
        return cartItemDao.create(item);
    }

    @Override
    public Boolean updateQty(Integer qty, Integer idCartItem) {
        return cartItemDao.updateQty(qty,idCartItem);
    }

    @Override
    public Boolean delete(Integer id) {
        return cartItemDao.delete(id);
    }
}
