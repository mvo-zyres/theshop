package com.onlineshop.theshop.shop.db.impl;

import com.onlineshop.theshop.shop.db.dao.OrderDAO;
import com.onlineshop.theshop.shop.db.dao.OrderItemDAO;
import com.onlineshop.theshop.shop.db.dao.OrderStatusDAO;
import com.onlineshop.theshop.shop.model.store.Order;
import com.onlineshop.theshop.shop.db.mapper.OrderIdRowMapper;
import com.onlineshop.theshop.shop.db.mapper.UserIdRowMapper;
import com.onlineshop.theshop.shop.db.mapper.OrderRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.UUID;

@Component
public class JdbcOrderDaoImpl implements OrderDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    OrderRowMapper orderRowMapper;

    @Autowired
    OrderStatusDAO orderStatusDAO;

    @Autowired
    OrderItemDAO orderItemDAO;

    @Autowired
    OrderIdRowMapper orderIdRowMapper;

    @Autowired
    UserIdRowMapper userIdRowMapper;

    private static final String CREATE = "INSERT INTO \"order\"(order_id, order_number, user_id) VALUES(?, ?, ?)";
    private static final String READ = "SELECT order_id, order_number FROM \"order\"";
    private static final String READ_BY_USER_ID = "SELECT order_id, order_number FROM \"order\" WHERE user_id = ?";
    private static final String READ_BY_ORDER_ID = "SELECT order_id, order_number FROM \"order\" WHERE order_id = ?";
    private static final String READ_USER_ID_BY_ORDER_ID = "SELECT user_id FROM \"order\" WHERE order_id = ?";
    private static final String DELETE = "DELETE FROM \"order\" WHERE order_id = ?";


    @Override
    public void create(Order order) {
        jdbcTemplate.update(CREATE, order.getId(), order.getOrderNumber() ,order.getUser().getId());
    }

    @Override
    public List<Order> read() {
        return jdbcTemplate.query(READ, orderRowMapper);
    }

    @Override
    public List<Order> readByUserId(UUID userId) {
        return jdbcTemplate.query(READ_BY_USER_ID, new Object[]{userId}, orderRowMapper);
    }

    @Override
    public Order readById(UUID orderId) {
        return jdbcTemplate.queryForObject(READ_BY_ORDER_ID, new Object[]{orderId}, orderRowMapper);
    }

    @Override
    public UUID readUserIdByOrderId(UUID orderId) {
        return jdbcTemplate.queryForObject(READ_USER_ID_BY_ORDER_ID, new Object[]{orderId}, userIdRowMapper);
    }

    @Override
    public void delete(UUID orderId) {
        jdbcTemplate.update(DELETE, orderId);
    }

}
