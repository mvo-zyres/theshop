package com.onlineshop.theshop.shop.db.mapper;

import com.onlineshop.theshop.shop.model.user.Privilege;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class PrivilegeRowMapper implements RowMapper<Privilege> {

    @Override
    public Privilege mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Privilege(
                UUID.fromString(rs.getString("privilege_id")),
                rs.getString("name")
        );
    }
}