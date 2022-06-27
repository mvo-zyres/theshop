package com.onlineshop.theshop.shop.db.dao;

import java.util.List;
import java.util.UUID;

public interface CartProductDAO {

    void create(UUID cartId, UUID productId);
    List<UUID> readByCartId(UUID cartId);
    List<UUID> readByProductId(UUID productId);
    void delete(UUID cartId, UUID productId);
}
