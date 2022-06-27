package com.onlineshop.theshop.shop.form.producttype;

public class AddProductTypeForm {
    private String type;
    private boolean shippingRequired;

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
