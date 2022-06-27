package com.onlineshop.theshop.shop.db.impl;

import com.onlineshop.theshop.shop.db.dao.OrderStatusDAO;
import com.onlineshop.theshop.shop.db.mapper.OrderStatusRowMapper;
import com.onlineshop.theshop.shop.model.store.OrderStatus;
import com.onlineshop.theshop.shop.db.mapper.OrderIdRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class JdbcOrderStatusDaoImpl implements OrderStatusDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    OrderStatusRowMapper orderStatusRowMapper;

    @Autowired
    OrderIdRowMapper orderIdRowMapper;

    private static final String CREATE = "INSERT INTO \"orderStatus\"(\"orderStatus_id\", name, \"time\", order_id) VALUES(?, ?, ?, ?)";
    private static final String READ = "SELECT \"orderStatus_id\", name, \"time\" FROM \"orderStatus\"";
    private static final String READ_BY_ORDER_ID = "SELECT \"orderStatus_id\", name, \"time\" FROM \"orderStatus\" WHERE order_id = ?";
    private static final String DELETE = "DELETE FROM \"orderStatus\" WHERE \"orderStatus_id\" = ?";
    private static final String READ_ORDER_ID_BY_ORDER_STATUS_ID = "SELECT order_id FROM \"orderStatus\" WHERE \"orderStatus_id\" = ?";

    @Override
    public void create(OrderStatus orderStatus) {
        jdbcTemplate.update(CREATE, orderStatus.getId(), orderStatus.getName(), orderStatus.getTime(), orderStatus.getOrder().getId());
    }

    @Override
    public List<OrderStatus> read() {
       return jdbcTemplate.query(READ, orderStatusRowMapper);
    }

    @Override
    public List<OrderStatus> readByOrderId(UUID orderId) {
        return jdbcTemplate.query(READ_BY_ORDER_ID, new Object[]{orderId}, orderStatusRowMapper);
    }

    @Override
    public UUID readOrderIdByOrderStatusId(UUID orderStatusId) {
        return jdbcTemplate.queryForObject(READ_ORDER_ID_BY_ORDER_STATUS_ID, new Object[]{orderStatusId}, orderIdRowMapper);
    }

    @Override
    public void delete(UUID orderStatusId) {
        jdbcTemplate.update(DELETE, orderStatusId);
    }

}
