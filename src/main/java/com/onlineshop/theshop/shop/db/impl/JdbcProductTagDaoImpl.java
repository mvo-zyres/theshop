package com.onlineshop.theshop.shop.db.impl;

import com.onlineshop.theshop.shop.db.dao.ProductTagDAO;
import com.onlineshop.theshop.shop.db.mapper.ProductTagRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class JdbcProductTagDaoImpl implements ProductTagDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ProductTagRowMapper productTagRowMapper;



    private static final String CREATE = "INSERT INTO product_tag(product_id, tag) VALUES(?, ?)";
    private static final String READ = "SELECT tag FROM product_tag";
    private static final String READ_BY_PRODUCT_ID = "SELECT tag FROM product_tag WHERE product_id = ?";
    private static final String DELETE = "DELETE FROM product_tag WHERE product_id = ?";




    @Override
    public void create(UUID productId, String tag) {
        jdbcTemplate.update(CREATE, productId, tag);
    }

    @Override
    public List<String> read() {
        return jdbcTemplate.query(READ, productTagRowMapper);
    }

    @Override
    public List<String> readByProductId(UUID productId) {
        return jdbcTemplate.query(READ_BY_PRODUCT_ID, new Object[]{productId}, productTagRowMapper);
    }

    @Override
    public void deleteByProductId(UUID productId) {
        jdbcTemplate.update(DELETE, productId);
    }
}
