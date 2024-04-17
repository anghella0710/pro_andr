package com.example.myapplication.Database.abstraction;

import com.example.myapplication.Model.Order;

import java.util.List;

public interface IOrderDatabase {
    public long createOrder(Order order);
    public int updateOrder(Order order);
    public Order readOrderById(int id);
    public List<Order> readAllOrder();
    public int deleteOrderById(int id);
}
