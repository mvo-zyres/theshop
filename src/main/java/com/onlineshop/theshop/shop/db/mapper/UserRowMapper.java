package com.onlineshop.theshop.shop.db.mapper;

import com.onlineshop.theshop.shop.db.dao.CartDAO;
import com.onlineshop.theshop.shop.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class UserRowMapper implements RowMapper<User> {

    @Autowired
    CartDAO cartDAO;

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User(
                UUID.fromString(rs.getString("user_id")),
                rs.getString("user_name"),
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("img"),
                rs.getBoolean("enabled"),
                rs.getBigDecimal("credits")
        );
        if (rs.getBigDecimal("credits") == null) {
            user.setCredits(BigDecimal.ZERO);
        }
        return user;
    }
}
