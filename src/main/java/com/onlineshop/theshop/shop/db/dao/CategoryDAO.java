package com.onlineshop.theshop.shop.db.dao;

import com.onlineshop.theshop.shop.model.store.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryDAO {

    void create(Category category);
    List<Category> read();
    Category readById(UUID categoryId);
    void update(Category category);
    void delete(UUID categoryId);
    List<Category> readByStoreId(UUID storeId);
    UUID readStoreIdByCategoryId(UUID categoryId);
    Category readByUrl(String url);
}
