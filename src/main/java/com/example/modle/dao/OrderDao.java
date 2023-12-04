package com.example.modle.dao;

import com.example.modle.entity.Order;

import java.util.List;

public interface OrderDao {
    public void clearCart(int cartId);

    public void createNewOrder(Order o) ;
 List<Order> findAllOrder();
}
