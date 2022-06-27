package com.onlineshop.theshop.shop.db.dao;

import com.onlineshop.theshop.shop.model.user.Role;

import java.util.List;
import java.util.UUID;


public interface RoleDAO {

    boolean roleExists(UUID id);

    void create(Role role);
    List<Role> read();
    Role readById(UUID roleId);
    void update(Role role);
    void delete(UUID roleId);


}
