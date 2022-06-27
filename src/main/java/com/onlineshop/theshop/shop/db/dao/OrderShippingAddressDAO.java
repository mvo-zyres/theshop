package com.onlineshop.theshop.shop.db.dao;

import com.onlineshop.theshop.shop.model.user.Address;

import java.util.UUID;

public interface OrderShippingAddressDAO {
    void create(Address address, UUID orderID);
    Address readByOrderId(UUID orderId);
    void delete(UUID orderId);
}
