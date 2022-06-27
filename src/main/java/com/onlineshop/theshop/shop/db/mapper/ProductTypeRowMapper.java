package com.onlineshop.theshop.shop.db.mapper;

import com.onlineshop.theshop.shop.model.store.ProductType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class ProductTypeRowMapper implements RowMapper<ProductType> {

    @Override
    public ProductType mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ProductType(UUID.fromString(rs.getString("product_type_id")), rs.getString("type"), rs.getBoolean("shipping_required"));
    }
}
