package com.onlineshop.theshop.shop.db.dao;

import com.onlineshop.theshop.shop.model.store.Store;

import java.util.List;
import java.util.UUID;

public interface StoreDAO {
    boolean storeExists(UUID id);
    void create(Store store);
    List<Store> read();
    Store readById(UUID storeId);
    void update(Store store);
    void delete(UUID storeId);
}
