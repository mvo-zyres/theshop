package com.onlineshop.theshop.shop.form.order;

import java.util.UUID;

public class AddOrderStatusForm {
    private UUID orderId;
    private String name;

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
