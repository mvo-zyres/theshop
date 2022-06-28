package com.onlineshop.theshop.shop.model.store;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Category implements Serializable {

    private UUID id;
    private String name;
    private String url;
    private List<Product> products;
    private Store store;
    private BigDecimal tax;

    public Category(UUID id, String name, String url, BigDecimal tax) {
        if(tax == null)
            tax = new BigDecimal(0);
        this.tax = tax;
        this.name = name;
        this.id = id;
        this.url = url;
    }
    public Category(String name, Store store, String url, BigDecimal tax) {
        if(tax == null)
            tax = new BigDecimal(0);
        this.tax = tax;
        this.name = name;
        this.store = store;
        this.id = UUID.randomUUID();
        this.url = url;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }
}
