package com.onlineshop.theshop.service;

import com.onlineshop.theshop.shop.model.store.Order;
import com.onlineshop.theshop.shop.model.store.OrderStatus;
import com.onlineshop.theshop.shop.db.dao.OrderDAO;
import com.onlineshop.theshop.shop.db.dao.OrderStatusDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderStatusService {

    @Autowired
    OrderDAO orderDAO;

    @Autowired
    OrderStatusDAO orderStatusDAO;

    public void loadOrder(OrderStatus orderStatus) {
        orderStatus.setOrder(new Order(orderStatusDAO.readOrderIdByOrderStatusId(orderStatus.getId())));
    }

    public void deleteOrderStatusById(UUID orderStatusId) {
        orderStatusDAO.delete(orderStatusId);
    }

    public void deleteOrderStatusesByOrderId(UUID orderId) {
        orderStatusDAO.readByOrderId(orderId)
                .forEach(orderStatus -> orderStatusDAO.delete(orderStatus.getId()));
    }

    public boolean orderStatusExists(UUID orderStatusId) {
        List<OrderStatus> orderStatusList = orderStatusDAO.read().stream().filter(orderStatus -> orderStatus.getId().toString().equals(orderStatusId.toString())).collect(Collectors.toList());
        return orderStatusList.size() == 1;
    }
}
