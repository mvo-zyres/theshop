package com.onlineshop.theshop.shop.form.store;

import java.math.BigDecimal;
import java.util.UUID;

public class EditStoreForm {
    private UUID storeId;
    private String name;
    private BigDecimal tax;

    public UUID getStoreId() {
        return storeId;
    }

    public void setStoreId(UUID storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }
}
