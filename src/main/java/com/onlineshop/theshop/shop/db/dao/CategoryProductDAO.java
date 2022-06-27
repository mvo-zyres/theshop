package com.onlineshop.theshop.shop.db.dao;

import java.util.List;
import java.util.UUID;

public interface CategoryProductDAO {

    void create(UUID categoryId, UUID productId);
    List<UUID> readByCategoryId(UUID categoryId);
    List<UUID> readByProductId(UUID productId);
    void delete(UUID categoryId, UUID productId);
    void deleteByCategoryId(UUID categoryId);
    void deleteByProductId(UUID productId);
}
