package com.onlineshop.theshop.shop.db.impl;

import com.onlineshop.theshop.shop.db.dao.CartProductDAO;
import com.onlineshop.theshop.shop.db.mapper.CartIdRowMapper;
import com.onlineshop.theshop.shop.db.mapper.ProductIdRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class JdbcCartProductDaoImpl implements CartProductDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ProductIdRowMapper productIdRowMapper;

    @Autowired
    CartIdRowMapper cartIdRowMapper;

    private static final String CREATE = "INSERT INTO cart_product(cart_id, product_id) VALUES(?, ?)";
    private static final String READ_PRODUCT_ID = "SELECT product_id FROM cart_product WHERE cart_id = ?";
    private static final String READ_CART_ID = "SELECT cart_id FROM cart_product WHERE product_id = ?";
    private static final String DELETE = "DELETE FROM cart_product WHERE cart_id = ? AND product_id = ?";

    @Override
    public void create(UUID cartId, UUID productId) {
        jdbcTemplate.update(CREATE, cartId, productId);
    }

    @Override
    public List<UUID> readByCartId(UUID cartId) {
        return jdbcTemplate.query(READ_PRODUCT_ID, new Object[]{cartId}, productIdRowMapper);
    }
    @Override
    public List<UUID> readByProductId(UUID productId) {
        return jdbcTemplate.query(READ_CART_ID, new Object[]{productId}, cartIdRowMapper);
    }

    @Override
    public void delete(UUID cartId, UUID productId) {
        jdbcTemplate.update(DELETE, cartId, productId);
    }
}
