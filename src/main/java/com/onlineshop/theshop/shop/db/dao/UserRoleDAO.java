package com.onlineshop.theshop.shop.db.dao;

import java.util.List;
import java.util.UUID;

public interface UserRoleDAO {

    void create(UUID userId, UUID roleId);
    List<UUID> readUserIdsByRoleId(UUID roleId);
    UUID readRoleIdByUserId(UUID userId);
    void delete(UUID userId, UUID roleId);
}
