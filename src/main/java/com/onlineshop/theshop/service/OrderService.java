package com.onlineshop.theshop.service;

import com.onlineshop.theshop.shop.db.dao.*;
import com.onlineshop.theshop.shop.model.store.Order;
import com.onlineshop.theshop.shop.model.store.OrderItem;
import com.onlineshop.theshop.shop.model.store.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    OrderItemDAO orderItemDAO;

    @Autowired
    OrderStatusDAO orderStatusDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    OrderDAO orderDAO;

    @Autowired
    BillingAddressService billingAddressService;

    @Autowired
    ShippingAddressService shippingAddressService;

    @Autowired
    OrderShippingAddressDAO orderShippingAddressDAO;

    @Autowired
    OrderBillingAddressDAO orderBillingAddressDAO;

    public void loadOrderItems(Order order) {
        order.setOrderItems(orderItemDAO.readByOrderId(order.getId()));
    }
    public void loadOrderStatuses(Order order) {
        order.setOrderStatuses(orderStatusDAO.readByOrderId(order.getId()));
    }
    public void loadUser(Order order) {
        order.setUser(userDAO.readById(orderDAO.readUserIdByOrderId(order.getId())));
    }
    public void loadAddress(Order order){
        order.setBillingaddress(orderShippingAddressDAO.readByOrderId(order.getId()));
        order.setShippingaddress(orderBillingAddressDAO.readByOrderId(order.getId()));
    }

    public void addOrder(Order order) {
        orderDAO.create(order);
        for (OrderItem orderItem : order.getOrderItems()) {
            orderItem.setOrder(order);
            orderItemDAO.create(orderItem);
        }
        for (OrderStatus orderStatus : order.getOrderStatuses()) {
            orderStatus.setOrder(order);
            addOrderStatus(orderStatus);
        }
        billingAddressService.loadUser(order.getBillingaddress());
        shippingAddressService.loadUser(order.getShippingaddress());
        //sketchy
        order.getBillingaddress().setAddressId(UUID.randomUUID());
        order.getShippingaddress().setAddressId(UUID.randomUUID());
        orderShippingAddressDAO.create(order.getShippingaddress(),order.getId());
        orderBillingAddressDAO.create(order.getBillingaddress(),order.getId());
    }

    public Order getOrderById(UUID orderId) {
        Order order = orderDAO.readById(orderId);
        loadOrderItems(order);
        loadOrderStatuses(order);
        loadUser(order);
        return order;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = orderDAO.read();
        orders.forEach(order -> {
            loadOrderItems(order);
            loadOrderStatuses(order);
            loadUser(order);
        });
        return orders;
    }

    public void deleteOrder(UUID orderId) {
        List<OrderItem> orderItems =  orderItemDAO.readByOrderId(orderId);
        List<OrderStatus> orderStatuses = orderStatusDAO.readByOrderId(orderId);
        orderBillingAddressDAO.delete(orderId);
        orderShippingAddressDAO.delete(orderId);
        orderItems.forEach(orderItem -> orderItemDAO.delete(orderItem.getId()));
        orderStatuses.forEach(orderStatus -> orderStatusDAO.delete(orderStatus.getId()));
        orderDAO.delete(orderId);
    }

    public void addOrderStatus(OrderStatus orderStatus) {
        orderStatusDAO.create(orderStatus);
    }

    public BigDecimal calcTotal(Order order){
        BigDecimal totalPrice = BigDecimal.ZERO;
        if (order.getOrderItems() != null) {
            for (OrderItem orderItem : order.getOrderItems()) {
                        totalPrice = totalPrice.add(orderItem.getPrice().multiply(BigDecimal.valueOf(100).add(orderItem.getTax()).divide(BigDecimal.valueOf(100))));
            }
        }
        return totalPrice;
    }

    public boolean orderExists(UUID orderId) {
        List<Order> orderList = orderDAO.read().stream().filter(order -> order.getId().toString().equals(orderId.toString())).collect(Collectors.toList());
        return orderList.size() == 1;
    }
}
