package com.onlineshop.theshop.shop.db.dao;

import com.onlineshop.theshop.shop.model.store.Order;

import java.util.List;
import java.util.UUID;

public interface OrderDAO {


    void create(Order order);
    List<Order> read();
    List<Order> readByUserId(UUID userId);
    Order readById(UUID orderId);
    UUID readUserIdByOrderId(UUID orderId);
    void delete(UUID orderId);
}
