package com.example.service;

import com.example.modle.entity.Order;

import java.util.List;

public interface OderService {
     void clearCart(int cartId);
     void createNewOrder(Order o) ;
     List<Order>findAllOrder();
}
