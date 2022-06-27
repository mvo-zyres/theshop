package com.onlineshop.theshop.shop.db.mapper;

import com.onlineshop.theshop.shop.model.store.Product;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class ProductRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Product(UUID.fromString(rs.getString("product_id")),
                rs.getString("name"),
                rs.getString("description"),
                new BigDecimal(rs.getString("price")),
                UUID.fromString(rs.getString("img")),
                rs.getBigDecimal("tax"));
    }
}
