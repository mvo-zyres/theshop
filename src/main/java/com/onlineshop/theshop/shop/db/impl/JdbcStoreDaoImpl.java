package com.onlineshop.theshop.shop.db.impl;

import com.onlineshop.theshop.shop.db.dao.CartDAO;
import com.onlineshop.theshop.shop.db.dao.CategoryDAO;
import com.onlineshop.theshop.shop.db.dao.StoreDAO;
import com.onlineshop.theshop.shop.model.store.Store;
import com.onlineshop.theshop.shop.db.mapper.StoreIdRowMapper;
import com.onlineshop.theshop.shop.db.mapper.StoreRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.UUID;

@Component
public class JdbcStoreDaoImpl implements StoreDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    StoreRowMapper storeRowMapper;

    @Autowired
    CartDAO cartDAO;

    @Autowired
    CategoryDAO categoryDAO;

    @Autowired
    StoreIdRowMapper storeIdRowMapper;

    private static final String SELECT_WHERE_ID = "SELECT store_id, name, tax FROM store WHERE store_id = ?";
    private static final String CREATE = "INSERT INTO store(store_id, name, tax) VALUES(?, ?, ?)";
    private static final String READ = "SELECT store_id, name, tax FROM store";
    private static final String READ_BY_ID = "SELECT store_id, name, tax FROM store WHERE store_id = ?";
    private static final String UPDATE = "UPDATE store SET name = ?, tax = ? WHERE store_id = ?";
    private static final String DELETE = "DELETE FROM store WHERE store_id = ?";



    @Override
    public void create(Store store) {
        jdbcTemplate.update(CREATE, store.getId(), store.getName(), store.getTax());
    }

    @Override
    public List<Store> read() {
        return jdbcTemplate.query(READ, storeRowMapper);
    }

    @Override
    public Store readById(UUID storeId) {
        return jdbcTemplate.queryForObject(READ_BY_ID, new Object[]{storeId}, storeRowMapper);
    }

    @Override
    public void update(Store store) {
        jdbcTemplate.update(UPDATE, store.getName(), store.getId(), store.getTax());
    }

    @Override
    public void delete(UUID storeId) {
        jdbcTemplate.update(DELETE, storeId);
    }

    @Override
    public boolean storeExists(UUID id) {
        return jdbcTemplate.query(SELECT_WHERE_ID, new Object[]{id}, storeRowMapper).size() == 1;
    }
}
