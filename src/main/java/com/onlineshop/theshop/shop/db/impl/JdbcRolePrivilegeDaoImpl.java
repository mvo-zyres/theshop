package com.onlineshop.theshop.shop.db.impl;

import com.onlineshop.theshop.shop.db.dao.RolePrivilegeDAO;
import com.onlineshop.theshop.shop.db.mapper.PrivilegeIdRowMapper;
import com.onlineshop.theshop.shop.db.mapper.RoleIdRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class JdbcRolePrivilegeDaoImpl implements RolePrivilegeDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    RoleIdRowMapper roleIdRowMapper;

    @Autowired
    PrivilegeIdRowMapper privilegeIdRowMapper;


    private static final String CREATE = "INSERT INTO role_privilege(role_id, privilege_id) VALUES(?, ?)";
    private static final String READ_BY_ROLE_ID = "SELECT privilege_id FROM role_privilege WHERE role_id = ?";
    private static final String READ_BY_PRIVILEGE_ID = "SELECT role_id FROM role_privilege WHERE privilege_id = ?";
    private static final String DELETE = "DELETE FROM role_privilege WHERE role_id = ? AND privilege_id = ?";


    @Override
    public void create(UUID roleId, UUID privilegeId) {
        jdbcTemplate.update(CREATE, roleId, privilegeId);
    }

    @Override
    public List<UUID> readByRoleId(UUID roleId) {
        return jdbcTemplate.query(READ_BY_ROLE_ID, new Object[]{roleId}, privilegeIdRowMapper);
    }

    @Override
    public List<UUID> readByPrivilegeId(UUID privilegeId) {
        return jdbcTemplate.query(READ_BY_PRIVILEGE_ID, new Object[]{privilegeId}, roleIdRowMapper);
    }

    @Override
    public void delete(UUID roleId, UUID privilegeId) {
        jdbcTemplate.update(DELETE, roleId, privilegeId);
    }
}
