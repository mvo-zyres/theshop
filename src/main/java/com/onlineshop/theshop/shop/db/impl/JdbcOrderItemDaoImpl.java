package com.onlineshop.theshop.shop.db.impl;

import com.onlineshop.theshop.service.OrderItemService;
import com.onlineshop.theshop.shop.db.dao.OrderItemDAO;
import com.onlineshop.theshop.shop.model.store.OrderItem;
import com.onlineshop.theshop.shop.db.mapper.OrderIdRowMapper;
import com.onlineshop.theshop.shop.db.mapper.OrderItemRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.UUID;

@Component
public class JdbcOrderItemDaoImpl implements OrderItemDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    OrderItemRowMapper orderItemRowMapper;

    @Autowired
    OrderIdRowMapper orderIdRowMapper;

    @Autowired
    OrderItemService orderItemService;

    private static final String CREATE = "INSERT INTO \"orderItem\"(\"orderItem_id\", name, description, price, img, \"referenceProduct_id\", order_id, tax) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String READ = "SELECT \"orderItem_id\", name, description, price, img, \"referenceProduct_id\", order_id, tax FROM \"orderItem\"";
    private static final String READ_BY_ORDER_ID = "SELECT \"orderItem_id\", name, description, price, img, \"referenceProduct_id\", order_id, tax FROM \"orderItem\" WHERE order_id = ?";
    private static final String READ_BY_PRODUCT_ID = "SELECT \"orderItem_id\", name, description, price, img, \"referenceProduct_id\", order_id, tax FROM \"orderItem\" WHERE \"referenceProduct_id\" = ?";
    private static final String DELETE = "DELETE FROM \"orderItem\" WHERE \"orderItem_id\" = ?";
    private static final String READ_ORDER_ID_BY_ORDER_ITEM_ID = "SELECT order_id FROM \"orderItem\" WHERE \"orderItem_id\" = ?";

    @Override
    public void create(OrderItem orderItem) {
        jdbcTemplate.update(CREATE,
                orderItem.getId(),
                orderItem.getName(),
                orderItem.getDescription(),
                orderItem.getPrice(),
                orderItem.getImg(),
                orderItem.getReferenceId(),
                orderItem.getOrder().getId(),
                orderItem.getTax());
    }

    @Override
    public List<OrderItem> read() {
        return jdbcTemplate.query(READ, orderItemRowMapper);
    }

    @Override
    public List<OrderItem> readByOrderId(UUID orderId) {
        return jdbcTemplate.query(READ_BY_ORDER_ID, new Object[]{orderId}, orderItemRowMapper);
    }

    @Override
    public List<OrderItem> readByProductId(UUID productId) {
        return jdbcTemplate.query(READ_BY_PRODUCT_ID, new Object[]{productId}, orderItemRowMapper);
    }

    @Override
    public UUID readOrderIdByOrderItemId(UUID orderItemId) {
        return jdbcTemplate.queryForObject(READ_ORDER_ID_BY_ORDER_ITEM_ID, new Object[]{orderItemId}, orderIdRowMapper);
    }

    @Override
    public void delete(UUID orderItemId) {
        jdbcTemplate.update(DELETE, orderItemId);
    }

    @Override
    public void deleteProductIdsFromOrderItemsByProductId(UUID productId) {
        List<OrderItem> orderItems = readByProductId(productId);
        orderItems.forEach(orderItem -> {
            delete(orderItem.getId());
            orderItemService.loadOrder(orderItem);
            orderItem.setReferenceId(null);
            create(orderItem);
        });
    }

}
