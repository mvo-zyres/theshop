package com.onlineshop.theshop.shop.db.mapper;

import com.onlineshop.theshop.shop.model.store.Cart;
import org.springframework.stereotype.Component;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class CartRowMapper implements RowMapper<Cart> {

    @Override
    public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Cart(UUID.fromString(rs.getString("cart_id")));
    }
}
