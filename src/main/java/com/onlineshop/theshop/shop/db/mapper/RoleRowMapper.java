package com.onlineshop.theshop.shop.db.mapper;

import com.onlineshop.theshop.shop.model.user.Role;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class RoleRowMapper implements RowMapper<Role> {
    @Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Role(
                UUID.fromString(rs.getString("role_id")),
                rs.getString("name")
        );
    }
}