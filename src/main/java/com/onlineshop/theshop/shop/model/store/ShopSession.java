package com.onlineshop.theshop.shop.model.store;

import com.onlineshop.theshop.shop.model.user.Address;
import com.onlineshop.theshop.shop.model.user.User;

import java.io.Serializable;
import java.util.UUID;

public class ShopSession implements Serializable {

    UUID sessionId;
    User user;
    Address currentbilling;
    Address currentshipping;
    String search;

    public ShopSession() {
        this.sessionId = UUID.randomUUID();
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Address getCurrentbilling() {
        return currentbilling;
    }

    public void setCurrentbilling(Address currentbilling) {
        this.currentbilling = currentbilling;
    }

    public Address getCurrentshipping() {
        return currentshipping;
    }

    public void setCurrentshipping(Address currentshipping) {
        this.currentshipping = currentshipping;
    }
}
