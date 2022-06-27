package com.onlineshop.theshop.shop.db.dao;

import com.onlineshop.theshop.shop.model.store.OrderItem;

import java.util.List;
import java.util.UUID;

public interface OrderItemDAO {

    void create(OrderItem orderItem);
    List<OrderItem> read();
    List<OrderItem> readByOrderId(UUID orderId);
    List<OrderItem> readByProductId(UUID productId);
    UUID readOrderIdByOrderItemId(UUID orderItemId);
    void delete(UUID orderItemId);
    void deleteProductIdsFromOrderItemsByProductId(UUID productId);
}
