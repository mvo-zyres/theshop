package com.onlineshop.theshop.shop.form.store;

import java.math.BigDecimal;

public class AddStoreForm {

    private String name;
    private BigDecimal tax;

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
