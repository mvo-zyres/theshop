package com.onlineshop.theshop.shop.db.impl;

import com.onlineshop.theshop.shop.db.dao.CartDAO;
import com.onlineshop.theshop.shop.model.store.Cart;
import com.onlineshop.theshop.shop.db.mapper.StoreIdRowMapper;
import com.onlineshop.theshop.shop.db.mapper.UserIdRowMapper;
import com.onlineshop.theshop.shop.db.mapper.CartRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.UUID;

@Component
public class JdbcCartDaoImpl implements CartDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    CartRowMapper cartRowMapper;

    @Autowired
    StoreIdRowMapper storeIdRowMapper;

    @Autowired
    UserIdRowMapper userIdRowMapper;


    private static final String CREATE = "INSERT INTO cart(cart_id, user_id, store_id) VALUES(?, ?, ?)";
    private static final String READ = "SELECT cart_id, user_id, store_id FROM cart";
    private static final String UPDATE = "UPDATE cart SET user_id = ?, store_id = ? WHERE cart_id = ?";
    private static final String DELETE = "DELETE FROM cart WHERE cart_id = ?";
    private static final String READ_USER_ID_BY_CART_ID = "SELECT user_id FROM cart WHERE cart_id = ?";
    private static final String READ_BY_STORE_ID = "SELECT cart_id, user_id, store_id FROM cart WHERE store_id = ?";
    private static final String READ_STORE_ID_BY_CART_ID = "SELECT store_id FROM cart WHERE cart_id = ?";
    private static final String READ_BY_USER_ID = "SELECT cart_id, user_id, store_id FROM cart WHERE user_id = ?";


    @Override
    public void create(UUID cartId, UUID userId, UUID storeId) {
        jdbcTemplate.update(CREATE, cartId, userId, storeId);
    }

    @Override
    public List<Cart> read() {
        return jdbcTemplate.query(READ, cartRowMapper);
    }

    @Override
    public void update(UUID cartId, UUID userId, UUID storeId) {
        jdbcTemplate.update(UPDATE, userId, storeId, cartId);
    }

    @Override
    public void delete(UUID cartId) {
        jdbcTemplate.update(DELETE, cartId);
    }

    @Override
    public List<Cart> readByStoreId(UUID storeId) {
        return jdbcTemplate.query(READ_BY_STORE_ID, new Object[]{storeId}, cartRowMapper);
    }

    @Override
    public UUID readStoreIdByCartId(UUID cartId) {
        return jdbcTemplate.queryForObject(READ_STORE_ID_BY_CART_ID, new Object[]{cartId}, storeIdRowMapper);
    }

    @Override
    public Cart readByUserId(UUID userId) {
        return jdbcTemplate.queryForObject(READ_BY_USER_ID, new Object[]{userId}, cartRowMapper);
    }

    @Override
    public UUID readUserIdByCartId(UUID cartId) {
        return jdbcTemplate.queryForObject(READ_USER_ID_BY_CART_ID, new Object[]{cartId}, userIdRowMapper);
    }
}
