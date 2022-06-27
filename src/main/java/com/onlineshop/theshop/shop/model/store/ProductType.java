package com.onlineshop.theshop.shop.model.store;

import java.io.Serializable;
import java.util.UUID;

public class ProductType implements Serializable {
    private UUID id;
    private String type;
    private boolean shippingRequired;


    public ProductType(UUID id, String type, boolean shippingRequired) {
        this.id = id;
        this.type = type;
        this.shippingRequired = shippingRequired;
    }

    public ProductType(String type, boolean shippingRequired) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.shippingRequired = shippingRequired;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isShippingRequired() {
        return shippingRequired;
    }

    public void setShippingRequired(boolean shippingRequired) {
        this.shippingRequired = shippingRequired;
    }
}
