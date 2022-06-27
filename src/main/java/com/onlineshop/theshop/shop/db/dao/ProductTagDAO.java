package com.onlineshop.theshop.shop.db.dao;

import java.util.List;
import java.util.UUID;

public interface ProductTagDAO {

    void create(UUID productId, String tag);
    List<String> read();
    List<String> readByProductId(UUID productId);
    void deleteByProductId(UUID productId);
}
