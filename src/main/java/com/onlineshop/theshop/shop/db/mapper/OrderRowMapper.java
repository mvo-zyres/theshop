package com.onlineshop.theshop.shop.db.mapper;

import com.onlineshop.theshop.shop.model.store.Order;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class OrderRowMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Order(UUID.fromString(rs.getString("order_id")),
                rs.getString("order_number")
        );
    }
}
