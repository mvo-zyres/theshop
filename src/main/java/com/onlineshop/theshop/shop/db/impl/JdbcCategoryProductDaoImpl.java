package com.onlineshop.theshop.shop.db.impl;

import com.onlineshop.theshop.shop.db.dao.CategoryProductDAO;
import com.onlineshop.theshop.shop.db.mapper.CategoryIdRowMapper;
import com.onlineshop.theshop.shop.db.mapper.ProductIdRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class JdbcCategoryProductDaoImpl implements CategoryProductDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ProductIdRowMapper productIdRowMapper;

    @Autowired
    CategoryIdRowMapper categoryIdRowMapper;

    private static final String CREATE = "INSERT INTO category_product(category_id, product_id) VALUES(?, ?)";
    private static final String READ_BY_CATEGORY_ID = "SELECT product_id FROM category_product WHERE category_id = ?";
    private static final String READ_BY_PRODUCT_ID = "SELECT category_id FROM category_product WHERE product_id = ?";
    private static final String DELETE = "DELETE FROM category_product WHERE category_id = ? AND product_id = ?";
    private static final String DELETE_BY_CATEGORY_ID = "DELETE FROM category_product WHERE category_id = ?";
    private static final String DELETE_BY_PRODUCT_ID = "DELETE FROM category_product WHERE product_id = ?";

    @Override
    public void create(UUID categoryId, UUID productId) {
        jdbcTemplate.update(CREATE, categoryId, productId);
    }

    @Override
    public List<UUID> readByCategoryId(UUID categoryId) {
        return jdbcTemplate.query(READ_BY_CATEGORY_ID, new Object[]{categoryId}, productIdRowMapper);
    }

    @Override
    public List<UUID> readByProductId(UUID productId) {
        return jdbcTemplate.query(READ_BY_PRODUCT_ID, new Object[]{productId}, categoryIdRowMapper);
    }

    @Override
    public void delete(UUID categoryId, UUID productId) {
        jdbcTemplate.update(DELETE, categoryId, productId);
    }

    @Override
    public void deleteByCategoryId(UUID categoryId) {
        jdbcTemplate.update(DELETE_BY_CATEGORY_ID, categoryId);
    }

    @Override
    public void deleteByProductId(UUID productId) {
        jdbcTemplate.update(DELETE_BY_PRODUCT_ID, productId);
    }
}
