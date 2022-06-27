package com.onlineshop.theshop.shop.model.store;

import com.onlineshop.theshop.shop.model.user.User;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class Cart implements Serializable {
    private UUID id;
    private List<Product> products;
    private User user;
    private Store store;

    public Cart(UUID id, User user, List<Product> products, Store store) {
        this.id = id;
        this.user = user;
        this.products = products;
        this.store = store;
    }
    public Cart(User user, List<Product> products, Store store) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.products = products;
        this.store = store;
    }


    public Cart(UUID id) {
        this.id = id;
    }



    public Cart() {
    }






    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
