package com.onlineshop.theshop.shop.db.mapper;

import com.onlineshop.theshop.shop.model.user.Address;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class AddressRowMapper implements RowMapper<Address> {
    @Override
    public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Address(UUID.fromString(rs.getString("address_id")),
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getString("line1"),
                rs.getString("line2"),
                rs.getString("line3"),
                rs.getString("city"),
                rs.getString("zip_or_postcode"),
                rs.getString("country_province"),
                rs.getString("country")
                );
    }
}
