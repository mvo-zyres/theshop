package com.onlineshop.theshop.shop.db.mapper;

import com.onlineshop.theshop.shop.db.dao.CartDAO;
import com.onlineshop.theshop.shop.db.dao.CategoryDAO;
import com.onlineshop.theshop.shop.model.store.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class StoreRowMapper implements RowMapper<Store> {

    @Autowired
    CategoryDAO categoryDAO;

    @Autowired
    CartDAO cartDAO;


    @Override
    public Store mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Store(UUID.fromString(rs.getString("store_id")),
                rs.getString("name"),
                rs.getBigDecimal("tax")
                );
    }
}
