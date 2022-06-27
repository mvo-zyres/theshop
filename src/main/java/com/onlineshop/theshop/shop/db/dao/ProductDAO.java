package com.onlineshop.theshop.shop.db.dao;

import com.onlineshop.theshop.shop.model.store.Product;

import java.util.List;
import java.util.UUID;

public interface ProductDAO {

    void create(Product product);
    List<Product> read();
    Product readById(UUID productId);
    List<Product> readByRecommended();
    List<UUID> readByCategoryId(UUID categoryId);
    UUID readTypeIdByProductId(UUID productId);
    List<UUID> readProductIdsByTypeId(UUID productTypeId);
    void update(Product product);
    void delete(UUID productId);
    void deleteTypeIdFromProductByTypeId(UUID productTypeId);
}
