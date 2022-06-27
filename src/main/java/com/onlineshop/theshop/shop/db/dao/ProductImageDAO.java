package com.onlineshop.theshop.shop.db.dao;

import java.util.List;
import java.util.UUID;

public interface ProductImageDAO {
    void create(UUID productId, UUID imageId);
    void deleteImageById(UUID imageId);
    List<UUID> readByProductId(UUID productId);

}
