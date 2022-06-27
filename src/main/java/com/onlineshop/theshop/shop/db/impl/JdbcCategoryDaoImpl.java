package com.onlineshop.theshop.shop.db.impl;

import com.onlineshop.theshop.shop.db.dao.CategoryDAO;
import com.onlineshop.theshop.shop.model.store.Category;
import com.onlineshop.theshop.shop.db.mapper.CategoryIdRowMapper;
import com.onlineshop.theshop.shop.db.mapper.CategoryRowMapper;
import com.onlineshop.theshop.shop.db.mapper.StoreIdRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.UUID;

@Component
public class JdbcCategoryDaoImpl implements CategoryDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    CategoryRowMapper categoryRowMapper;

     @Autowired
     CategoryIdRowMapper categoryIdRowMapper;

     @Autowired
    StoreIdRowMapper storeIdRowMapper;

    private static final String CREATE = "INSERT INTO category(category_id, name, store_id, url, tax) VALUES(?, ?, ?, ?, ?)";
    private static final String READ = "SELECT category_id, name, url, tax From category";
    private static final String READ_BY_ID = "SELECT category_id, name, url, tax From category WHERE category_id = ?";
    private static final String UPDATE = "UPDATE category SET name = ?, store_id = ?, url = ?, tax = ? WHERE category_id = ?";
    private static final String READ_STORE_ID_BY_CATEGORY_ID = "SELECT store_id FROM category WHERE category_id = ?";
    private static final String DELETE = "DELETE FROM category WHERE category_id = ?";
    private static final String READ_BY_STORE_ID = "SELECT category_id, name, url, tax From category WHERE store_id = ?";
    private static final String READ_BY_URL = "SELECT category_id, name, url, tax FROM category WHERE url = ?";


    @Override
    public void create(Category category) {
        jdbcTemplate.update(CREATE,category.getId(), category.getName(), category.getStore().getId(), category.getUrl(), category.getTax());
    }

    @Override
    public List<Category> read() {
        return jdbcTemplate.query(READ, categoryRowMapper);
    }

    @Override
    public Category readById(UUID categoryId) {
        return jdbcTemplate.queryForObject(READ_BY_ID, new Object[]{categoryId}, categoryRowMapper);
    }

    @Override
    public void update(Category category) {
        jdbcTemplate.update(UPDATE, category.getName(), category.getStore().getId(), category.getUrl(), category.getId(), category.getTax());
    }

    @Override
    public void delete(UUID categoryId) {
        jdbcTemplate.update(DELETE, categoryId);
    }

    @Override
    public List<Category> readByStoreId(UUID storeId) {
        return jdbcTemplate.query(READ_BY_STORE_ID, new Object[]{storeId}, categoryRowMapper);
    }

    @Override
    public UUID readStoreIdByCategoryId(UUID categoryId) {
        return jdbcTemplate.queryForObject(READ_STORE_ID_BY_CATEGORY_ID, new Object[]{categoryId}, storeIdRowMapper);
    }

    @Override
    public Category readByUrl(String url) {
        return jdbcTemplate.queryForObject(READ_BY_URL, new Object[]{url}, categoryRowMapper);
    }
}
