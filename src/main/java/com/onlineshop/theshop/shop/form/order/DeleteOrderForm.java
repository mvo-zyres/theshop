package com.onlineshop.theshop.shop.form.order;

import java.util.UUID;

public class DeleteOrderForm {
    private UUID orderId;

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }
}
