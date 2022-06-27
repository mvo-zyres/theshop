package com.onlineshop.theshop.shop.db.mapper;

import com.onlineshop.theshop.shop.model.store.OrderItem;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class OrderItemRowMapper implements RowMapper<OrderItem> {

    @Override
    public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new OrderItem(UUID.fromString(rs.getString("orderItem_id")),
                rs.getString("name"),
                rs.getString("description"),
                new BigDecimal(rs.getString("price")),
                UUID.fromString(rs.getString("img")),
                UUID.fromString(rs.getString("referenceProduct_id")),
                new BigDecimal(rs.getString("tax")));
    }
}
