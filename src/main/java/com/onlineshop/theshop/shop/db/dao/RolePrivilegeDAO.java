package com.onlineshop.theshop.shop.db.dao;

import java.util.List;
import java.util.UUID;

public interface RolePrivilegeDAO {
    void create(UUID roleId, UUID privilegeId);
    List<UUID> readByRoleId(UUID roleId);
    List<UUID> readByPrivilegeId(UUID privilegeId);
    void delete(UUID roleId, UUID privilegeId);

}
