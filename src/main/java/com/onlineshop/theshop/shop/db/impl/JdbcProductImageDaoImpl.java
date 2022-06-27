package com.onlineshop.theshop.shop.db.impl;

import com.onlineshop.theshop.shop.db.dao.ProductImageDAO;
import com.onlineshop.theshop.shop.db.mapper.ProductImageRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class JdbcProductImageDaoImpl implements ProductImageDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ProductImageRowMapper productImageRowMapper;

    private static final String CREATE = "INSERT INTO product_image(product_id, image_id) VALUES(?, ?);";
    private static final String DELETE = "DELETE FROM product_image WHERE image_id = ?;";
    private static final String READ_BY_PRODUCT_ID = "SELECT image_id FROM product_image WHERE product_id = ?;";

    @Override
    public void create(UUID productId, UUID imageId) {
        jdbcTemplate.update(CREATE, productId, imageId);
    }

    @Override
    public void deleteImageById(UUID imageId) {
        jdbcTemplate.update(DELETE, imageId);
    }

    @Override
    public List<UUID> readByProductId(UUID productId) {
        return jdbcTemplate.query(READ_BY_PRODUCT_ID, new Object[]{productId}, productImageRowMapper);
    }
}
