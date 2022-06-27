package com.onlineshop.theshop.shop.model.store;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class OrderStatus implements Serializable {
    private UUID id;
    private String name;
    private Timestamp time;
    private Order order;

    public OrderStatus(String name, Order order) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.time = Timestamp.valueOf(LocalDateTime.now());
        this.order = order;
    }

    public OrderStatus(UUID id, String name, Timestamp time) {
        this.id = id;
        this.name = name;
        this.time = time;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
