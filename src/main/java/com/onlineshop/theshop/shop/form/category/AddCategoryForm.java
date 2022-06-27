package com.onlineshop.theshop.shop.form.category;

import java.math.BigDecimal;
import java.util.UUID;

public class AddCategoryForm {

    private String name;
    private String url;
    private BigDecimal tax;
    private UUID storeId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public UUID getStoreId() {
        return storeId;
    }

    public void setStoreId(UUID storeId) {
        this.storeId = storeId;
    }
    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }
}
