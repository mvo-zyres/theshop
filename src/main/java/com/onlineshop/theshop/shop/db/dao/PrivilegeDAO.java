package com.onlineshop.theshop.shop.db.dao;

import com.onlineshop.theshop.shop.model.user.Privilege;

import java.util.List;
import java.util.UUID;


public interface PrivilegeDAO {

    boolean privilegeExists(UUID id);
    void create(Privilege privilege);
    List<Privilege> read();
    Privilege readById(UUID privilegeId);
    void update(Privilege privilege);
    void delete(UUID privilegeId);

}