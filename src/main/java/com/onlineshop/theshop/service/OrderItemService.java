package com.onlineshop.theshop.service;

import com.onlineshop.theshop.shop.model.store.Order;
import com.onlineshop.theshop.shop.model.store.OrderItem;
import com.onlineshop.theshop.shop.db.dao.OrderDAO;
import com.onlineshop.theshop.shop.db.dao.OrderItemDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderItemService {
    @Autowired
    OrderDAO orderDAO;

    @Autowired
    OrderItemDAO orderItemDAO;

    @Autowired
    OrderService orderService;

    public void loadOrder(OrderItem orderItem) {
        orderItem.setOrder(new Order(orderItemDAO.readOrderIdByOrderItemId(orderItem.getId())));
    }

    public void deleteOrderItemById(UUID orderItemId) {
        orderItemDAO.delete(orderItemId);
    }

    public void deleteOrderItemsByOrderId(UUID orderId) {
        orderItemDAO.readByOrderId(orderId)
                .forEach(orderItem -> orderItemDAO.delete(orderItem.getId()));
    }

    public boolean orderItemExists(UUID orderItemId) {
        List<OrderItem> orderItemList = orderItemDAO.read().stream().filter(orderItem -> orderItem.getId().toString().equals(orderItemId.toString())).collect(Collectors.toList());
        return orderItemList.size() == 1;
    }
}
