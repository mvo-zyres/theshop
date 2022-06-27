package com.onlineshop.theshop.shop.db.mapper;

import com.onlineshop.theshop.shop.model.store.OrderStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class OrderStatusRowMapper implements RowMapper<OrderStatus> {


    @Override
    public OrderStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new OrderStatus(UUID.fromString(rs.getString("orderStatus_id")),
                rs.getString("name"),
                rs.getTimestamp("time"));
    }
}
