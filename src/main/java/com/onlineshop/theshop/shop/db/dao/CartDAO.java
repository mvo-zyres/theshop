package com.onlineshop.theshop.shop.db.dao;

import com.onlineshop.theshop.shop.model.store.Cart;

import java.util.List;
import java.util.UUID;

public interface CartDAO {

    void create(UUID cartId, UUID userId, UUID storeId);
    List<Cart> read();
    void update(UUID cartId, UUID userId, UUID storeId);
    void delete(UUID cartId);
    List<Cart> readByStoreId(UUID storeId);
    UUID readStoreIdByCartId(UUID cartId);
    Cart readByUserId(UUID userId);
    UUID readUserIdByCartId(UUID cartId);
}
