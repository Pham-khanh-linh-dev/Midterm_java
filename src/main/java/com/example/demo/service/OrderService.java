package com.example.demo.service;

import com.example.demo.model.Order;

import java.util.List;

public interface OrderService {
    void save(Order order);
    List<Order> getAllOrder();
}
