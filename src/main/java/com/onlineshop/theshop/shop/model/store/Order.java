package com.onlineshop.theshop.shop.model.store;

import com.onlineshop.theshop.shop.model.user.Address;
import com.onlineshop.theshop.shop.model.user.User;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Order implements Serializable {
    private UUID id;
    private String orderNumber;
    private Address billingaddress;
    private Address shippingaddress;
    private List<OrderItem> orderItems;
    private List<OrderStatus> orderStatuses;
    private User user;
    private Store store;
    private final Random rnd = new Random();  // SecureRandom is preferred to Random

    public Order() {
    }


    public Order(UUID id, String orderNumber) {
        this.id = id;
        this.orderNumber = orderNumber;
    }

    public Order(UUID id) {
        this.id = id;
    }
    public Order(String orderNumber){
        this.orderNumber = orderNumber;
    }
    public Order(UUID id, String orderNumber, Address billingaddress, Address shippingaddress) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.billingaddress = billingaddress;
        this.shippingaddress = shippingaddress;
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<OrderStatus> getOrderStatuses() {
        return orderStatuses;
    }
    public void setOrderStatuses(List<OrderStatus> orderStatuses) {
        this.orderStatuses = orderStatuses;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getRandomOrderNumber() {
        int number = rnd.nextInt(999999);
        return "O-" + String.format("%06d", number);
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Address getBillingaddress() {
        return billingaddress;
    }

    public void setBillingaddress(Address billingaddress) {
        this.billingaddress = billingaddress;
    }

    public Address getShippingaddress() {
        return shippingaddress;
    }

    public void setShippingaddress(Address shippingaddress) {
        this.shippingaddress = shippingaddress;
    }
}
