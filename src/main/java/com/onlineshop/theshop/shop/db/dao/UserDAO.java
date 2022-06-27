package com.onlineshop.theshop.shop.db.dao;

import com.onlineshop.theshop.shop.model.user.User;

import java.util.List;
import java.util.UUID;

public interface UserDAO {

    boolean nameExists(String username);
    void create(User user);
    List<User> read();
    User readById(UUID userId);
    void update(User user);
    void delete(UUID userId);
}
