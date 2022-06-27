package com.onlineshop.theshop.shop.model.store;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Store implements Serializable {

    private UUID id;
    private String name;
    private List<Category> categories;
    private List<Cart> carts;
    private BigDecimal tax;

    public Store(UUID id, String name, BigDecimal tax) {
        if(tax == null)
            tax = new BigDecimal(0);
        this.tax = tax.divide(new BigDecimal(100)).add(new BigDecimal(1));
        this.id = id;
        this.name = name;
    }

    public Store(String name, BigDecimal tax) {
        if(tax == null)
            tax = new BigDecimal(0);
        this.tax = tax.divide(new BigDecimal(100)).add(new BigDecimal(1));
        this.id = UUID.randomUUID();
        this.name = name;
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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }
}
