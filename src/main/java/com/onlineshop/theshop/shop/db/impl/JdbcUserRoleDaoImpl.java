package com.onlineshop.theshop.shop.db.impl;

import com.onlineshop.theshop.shop.db.dao.UserRoleDAO;
import com.onlineshop.theshop.shop.db.mapper.RoleIdRowMapper;
import com.onlineshop.theshop.shop.db.mapper.UserIdRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class JdbcUserRoleDaoImpl implements UserRoleDAO {


    @Autowired
    RoleIdRowMapper roleIdRowMapper;

    @Autowired
    UserIdRowMapper userIdRowMapper;

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String CREATE = "INSERT INTO user_role(user_id, role_id) VALUES(?, ?)";
    private static final String READ_USER_IDS_BY_ROLE_ID = "SELECT user_id FROM user_role WHERE role_id = ?";
    private static final String READ_ROLE_ID_BY_USER_ID = "SELECT role_id FROM user_role WHERE user_id = ?";
    private static final String DELETE = "DELETE FROM user_role WHERE user_id = ? AND role_id = ?";




    @Override
    public void create(UUID userId, UUID roleId) {
        jdbcTemplate.update(CREATE, userId, roleId);
    }

    @Override
    public List<UUID> readUserIdsByRoleId(UUID roleId) {
        return jdbcTemplate.query(READ_USER_IDS_BY_ROLE_ID, new Object[]{roleId}, userIdRowMapper);
    }

    @Override
    public UUID readRoleIdByUserId(UUID userId) {
        return jdbcTemplate.queryForObject(READ_ROLE_ID_BY_USER_ID, new Object[]{userId}, roleIdRowMapper);
    }

    @Override
    public void delete(UUID userId, UUID roleId) {
        jdbcTemplate.update(DELETE, userId, roleId);
    }
}
