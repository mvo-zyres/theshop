package com.onlineshop.theshop.shop.db.mapper;

import com.onlineshop.theshop.shop.model.store.Category;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class CategoryRowMapper implements RowMapper<Category> {

    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Category(UUID.fromString(rs.getString("category_id")),
                rs.getString("name"),
                rs.getString("url"),
                rs.getBigDecimal("tax")
        );
    }
}
