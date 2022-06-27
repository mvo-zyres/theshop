package com.onlineshop.theshop.shop.db.impl;

import com.onlineshop.theshop.shop.db.dao.CategoryDAO;
import com.onlineshop.theshop.shop.db.dao.ProductDAO;
import com.onlineshop.theshop.shop.model.store.Product;
import com.onlineshop.theshop.shop.db.mapper.ProductIdRowMapper;
import com.onlineshop.theshop.shop.db.mapper.ProductRowMapper;
import com.onlineshop.theshop.shop.db.mapper.ProductTypeIdRowMapper;
import com.onlineshop.theshop.shop.db.mapper.ProductTagRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.UUID;

@Component
public class JdbcProductDaoImpl implements ProductDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    ProductRowMapper productRowMapper;

    @Autowired
    CategoryDAO categoryDAO;

    @Autowired
    ProductIdRowMapper productIdRowMapper;

    @Autowired
    ProductTagRowMapper productTagRowMapper;

    @Autowired
    ProductTypeIdRowMapper productTypeIdRowMapper;

    private static final String CREATE = "INSERT INTO product(product_id, name, description, price, img, product_type_id, tax, recommended) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String READ = "SELECT product_id, name, description, price, img, recommended, tax FROM product";
    private static final String READ_BY_CATEGORY_ID = "SELECT product_id FROM category_product WHERE category_id = ?";
    private static final String UPDATE = "UPDATE product SET name = ?, description = ?, price = ?, img = ?, recommended = ?, product_type_id = ?, tax = ? WHERE product_id = ?";
    private static final String DELETE = "DELETE FROM product WHERE product_id = ?";
    private static final String READ_BY_ID = "SELECT product_id, name, description, price, img, tax FROM product WHERE product_id = ?";
    private static final String READ_BY_RECOMMENDED = "SELECT product_id, name, description, price, img, tax FROM product WHERE recommended = true";
    private static final String DELETE_TYPE_ID_FROM_PRODUCT_BY_TYPE_ID = "UPDATE product SET product_type_id = '' WHERE product_type_id = ?";
    private static final String READ_TYPE_ID_BY_PRODUCT_ID = "SELECT product_type_id FROM product WHERE product_id = ?";
    private static final String READ_PRODUCT_IDS_BY_TYPE_ID = "SELECT product_id FROM product WHERE product_type_id = ?";


    @Override
    public void create(Product product) {
        jdbcTemplate.update(CREATE, product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getImg(), product.getProductType().getId(), product.getTax(), false);
    }

    @Override
    public List<Product> read() {
        return jdbcTemplate.query(READ, productRowMapper);
    }

    @Override
    public Product readById(UUID productId) {
        return jdbcTemplate.queryForObject(READ_BY_ID, new Object[]{productId}, productRowMapper);
    }

    @Override
    public List<UUID> readByCategoryId(UUID categoryId) {
        return jdbcTemplate.query(READ_BY_CATEGORY_ID, new Object[]{categoryId}, productIdRowMapper);
    }

    @Override
    public List<Product> readByRecommended() {
        return jdbcTemplate.query(READ_BY_RECOMMENDED, productRowMapper);
    }

    @Override
    public void update(Product product) {
        jdbcTemplate.update(UPDATE, product.getName(), product.getDescription(), product.getPrice(), product.getImg(), product.isRecommended(), product.getProductType().getId(), product.getTax(), product.getId());
    }

    @Override
    public void delete(UUID productId) {
        jdbcTemplate.update(DELETE, productId);
    }

    @Override
    public UUID readTypeIdByProductId(UUID productId) {
        return jdbcTemplate.queryForObject(READ_TYPE_ID_BY_PRODUCT_ID, new Object[]{productId}, productTypeIdRowMapper);
    }

    @Override
    public List<UUID> readProductIdsByTypeId(UUID productTypeId) {
        return jdbcTemplate.query(READ_PRODUCT_IDS_BY_TYPE_ID, new Object[]{productTypeId}, productIdRowMapper);
    }

    @Override
    public void deleteTypeIdFromProductByTypeId(UUID productTypeId) {
        jdbcTemplate.update(DELETE_TYPE_ID_FROM_PRODUCT_BY_TYPE_ID, productTypeId);
    }


}
