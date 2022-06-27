package com.onlineshop.theshop.shop.form.producttype;

import java.util.UUID;

public class EditProductTypeForm {
    private UUID productTypeId;
    private String type;
    private boolean shippingRequired;

    public UUID getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(UUID productTypeId) {
        this.productTypeId = productTypeId;
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
