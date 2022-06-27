package com.onlineshop.theshop.shop.db.impl;

import com.onlineshop.theshop.shop.db.dao.ProductTypeDAO;
import com.onlineshop.theshop.shop.model.store.ProductType;
import com.onlineshop.theshop.shop.db.mapper.ProductTypeRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class JdbcProductTypeDaoImpl implements ProductTypeDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ProductTypeRowMapper productTypeRowMapper;


    private static final String CREATE = "INSERT INTO product_type(product_type_id, type, shipping_required) VALUES(?, ?, ?)";
    private static final String READ = "SELECT product_type_id, type, shipping_required FROM product_type";
    private static final String READ_BY_ID = "SELECT product_type_id, type, shipping_required FROM product_type WHERE product_type_id = ?";
    private static final String UPDATE = "UPDATE product_type SET type = ?, shipping_required = ? WHERE product_type_id = ?";
    private static final String DELETE = "DELETE FROM product_type WHERE product_type_id = ?";

    @Override
    public void create(ProductType productType) {
        jdbcTemplate.update(CREATE, productType.getId(), productType.getType(), productType.isShippingRequired());
    }

    @Override
    public List<ProductType> read() {
        return jdbcTemplate.query(READ, productTypeRowMapper);
    }

    @Override
    public ProductType readById(UUID productTypeId) {
        return jdbcTemplate.queryForObject(READ_BY_ID, new Object[]{productTypeId}, productTypeRowMapper);
    }

    @Override
    public void update(ProductType productType) {
        jdbcTemplate.update(UPDATE, productType.getType(), productType.isShippingRequired(), productType.getId());
    }

    @Override
    public void delete(UUID productTypeId) {
        jdbcTemplate.update(DELETE, productTypeId);
    }
}
