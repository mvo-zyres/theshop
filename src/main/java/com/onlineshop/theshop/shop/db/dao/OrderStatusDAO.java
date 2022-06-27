package com.onlineshop.theshop.shop.db.dao;

import com.onlineshop.theshop.shop.model.store.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface OrderStatusDAO {

    void create(OrderStatus orderStatus);
    List<OrderStatus> read();
    List<OrderStatus> readByOrderId(UUID orderId);
    UUID readOrderIdByOrderStatusId(UUID orderStatusId);
    void delete(UUID orderStatusId);
}
