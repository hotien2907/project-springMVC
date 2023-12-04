package com.example.service;

import com.example.modle.dao.OrderDao;
import com.example.modle.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OderServiceImpl implements  OderService {
    @Autowired
    OrderDao orderDao;
    @Override
    public void clearCart(int cartId) {
        orderDao.clearCart(cartId);
    }

    @Override
    public void createNewOrder(Order order) {
        orderDao.createNewOrder(order);
    }

    @Override
    public List<Order> findAllOrder() {
        return orderDao.findAllOrder();
    }
}
