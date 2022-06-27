package com.onlineshop.theshop.shop.db.dao;

import com.onlineshop.theshop.shop.model.user.Address;

import java.util.List;
import java.util.UUID;

public interface BillingAddressDAO {

    void create(Address address);
    List<Address> read();
    List<Address> readByUserId(UUID userId);
    Address readByAddressId(UUID addressId);
    UUID readDefaultBillingAddressIdByUserId(UUID userId);
    void setDefaultBillingAddress(UUID addressId, UUID userId);
    void update(Address address);
    void delete(UUID addressId);
    UUID readUserIdByAddressId(UUID addressId);
}
