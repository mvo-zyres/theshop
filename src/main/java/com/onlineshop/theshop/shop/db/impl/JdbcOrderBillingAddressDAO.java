package com.onlineshop.theshop.shop.db.impl;

import com.onlineshop.theshop.shop.db.dao.OrderBillingAddressDAO;
import com.onlineshop.theshop.shop.model.user.Address;
import com.onlineshop.theshop.shop.db.mapper.AddressRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JdbcOrderBillingAddressDAO implements OrderBillingAddressDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    AddressRowMapper addressRowMapper;

    private static final String CREATE = "INSERT INTO order_billing_address(address_id, order_id, firstname, lastname, line1, line2, line3, city, zip_or_postcode, country_province, country) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String READ_BY_ORDER_ID = "SELECT address_id, order_id, firstname, lastname, line1, line2, line3, city, zip_or_postcode, country_province, country FROM order_billing_address WHERE order_id = ?";
    private static final String DELETE = "DELETE FROM order_billing_address WHERE address_id = ?";
    private static final String DELETE_WHERE_ORDER_ID = "DELETE FROM order_billing_address WHERE order_id = ?";

    @Override
    public void create(Address address, UUID orderID) {
        jdbcTemplate.update(CREATE, address.getAddressId(), orderID, address.getFirstname(), address.getLastname(), address.getLine1(), address.getLine2(), address.getLine3(), address.getCity(), address.getZipOrPostcode(), address.getCountryProvince(), address.getCountry());
    }

    @Override
    public Address readByOrderId(UUID orderId) {
        return jdbcTemplate.queryForObject(READ_BY_ORDER_ID, new Object[]{orderId}, addressRowMapper);
    }

    @Override
    public void delete(UUID orderId) {
        jdbcTemplate.update(DELETE_WHERE_ORDER_ID, orderId);
    }
}
