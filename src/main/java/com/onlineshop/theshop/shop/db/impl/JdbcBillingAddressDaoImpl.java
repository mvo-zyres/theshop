package com.onlineshop.theshop.shop.db.impl;

import com.onlineshop.theshop.shop.db.dao.BillingAddressDAO;
import com.onlineshop.theshop.shop.model.user.Address;
import com.onlineshop.theshop.shop.db.mapper.AddressIdRowMapper;
import com.onlineshop.theshop.shop.db.mapper.AddressRowMapper;
import com.onlineshop.theshop.shop.db.mapper.UserIdRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class JdbcBillingAddressDaoImpl implements BillingAddressDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    AddressRowMapper addressRowMapper;

    @Autowired
    AddressIdRowMapper addressIdRowMapper;

    @Autowired
    UserIdRowMapper userIdRowMapper;

    private static final String CREATE = "INSERT INTO user_billing_address(address_id, user_id, firstname, lastname, line1, line2, line3, city, zip_or_postcode, country_province, country) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String READ = "SELECT address_id, user_id, firstname, lastname, line1, line2, line3, city, zip_or_postcode, country_province, country FROM user_billing_address";
    private static final String READ_BY_ADDRESS_ID = "SELECT address_id, user_id, firstname, lastname, line1, line2, line3, city, zip_or_postcode, country_province, country FROM user_billing_address WHERE address_id = ?";
    private static final String READ_BY_USER_ID = "SELECT address_id, user_id, firstname, lastname, line1, line2, line3, city, zip_or_postcode, country_province, country FROM user_billing_address WHERE user_id = ?";
    private static final String UPDATE = "UPDATE user_billing_address SET firstname = ?, lastname = ?, line1 = ?, line2 = ?, line3 = ?, city = ?, zip_or_postcode = ?, country_province = ?, country = ? WHERE address_id = ?";
    private static final String DELETE = "DELETE FROM user_billing_address WHERE address_id = ?";
    private static final String READ_DEFAULT_BILLING_ADDRESS_ID_FROM_USERID = "SELECT default_billing_address_id AS address_id FROM \"user\" WHERE user_id = ?";
    private static final String SET_DEFAULT_BILLING_ADDRESS = "UPDATE \"user\" SET default_billing_address_id = ? WHERE user_id = ?";
    private static final String READ_USER_ID_BY_ADDRESS_ID = "SELECT user_id FROM user_billing_address WHERE address_id = ?";



    @Override
    public void create(Address address) {
        jdbcTemplate.update(CREATE,
                address.getAddressId(),
                address.getUser().getId(),
                address.getFirstname(),
                address.getLastname(),
                address.getLine1(),
                address.getLine2(),
                address.getLine3(),
                address.getCity(),
                address.getZipOrPostcode(),
                address.getCountryProvince(),
                address.getCountry());
    }

    @Override
    public List<Address> read() {
        return jdbcTemplate.query(READ, addressRowMapper);
    }

    @Override
    public List<Address> readByUserId(UUID userId) {
        return jdbcTemplate.query(READ_BY_USER_ID, new Object[]{userId}, addressRowMapper);
    }

    @Override
    public Address readByAddressId(UUID addressId) {
        return jdbcTemplate.queryForObject(READ_BY_ADDRESS_ID, new Object[]{addressId}, addressRowMapper);
    }

    @Override
    public UUID readDefaultBillingAddressIdByUserId(UUID userId) {
        return jdbcTemplate.queryForObject(READ_DEFAULT_BILLING_ADDRESS_ID_FROM_USERID, new Object[]{userId}, addressIdRowMapper);
    }

    @Override
    public void setDefaultBillingAddress(UUID addressId, UUID userId) {
        jdbcTemplate.update(SET_DEFAULT_BILLING_ADDRESS, addressId, userId);
    }

    @Override
    public void update(Address address) {
        jdbcTemplate.update(UPDATE, address.getFirstname(), address.getLastname(), address.getLine1(), address.getLine2(), address.getLine3(), address.getCity(), address.getZipOrPostcode(), address.getCountryProvince(), address.getCountry(), address.getAddressId());
    }

    @Override
    public void delete(UUID addressId) {
        jdbcTemplate.update(DELETE, addressId);
    }
    @Override
    public UUID readUserIdByAddressId(UUID addressId) {
        return jdbcTemplate.queryForObject(READ_USER_ID_BY_ADDRESS_ID, new Object[]{addressId}, userIdRowMapper);
    }
}
