package com.onlineshop.theshop.shop.db.impl;

import com.onlineshop.theshop.shop.db.dao.PrivilegeDAO;
import com.onlineshop.theshop.shop.db.dao.RoleDAO;
import com.onlineshop.theshop.shop.model.user.Role;
import com.onlineshop.theshop.shop.db.mapper.PrivilegeRowMapper;
import com.onlineshop.theshop.shop.db.mapper.UserRowMapper;
import com.onlineshop.theshop.shop.db.mapper.RoleRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class JdbcRoleDAOImpl implements RoleDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RoleRowMapper roleRowMapper;

    @Autowired
    private UserRowMapper userRowMapper;

    @Autowired
    private PrivilegeRowMapper privilegeRowMapper;

    @Autowired
    private PrivilegeDAO privilegeDao;

    private static final String SELECT_WHERE_ID = "SELECT role_id, name FROM role WHERE role_id = ?";
    private static final String CREATE = "INSERT INTO role (role_id, name) VALUES (?, ?)";
    private static final String READ = "SELECT role_id, name FROM role";
    private static final String READ_BY_ID = "SELECT role_id, name FROM role WHERE role_id = ?";
    private static final String UPDATE = "UPDATE role SET name = ? WHERE role_id = ?";
    private static final String DELETE = "DELETE FROM role WHERE role_id = ?";

    @Override
    public void create(Role role) {
        jdbcTemplate.update(CREATE, role.getId(), role.getName());
    }

    @Override
    public List<Role> read() {
        return jdbcTemplate.query(READ, roleRowMapper);
    }

    @Override
    public Role readById(UUID roleId) {
        return jdbcTemplate.queryForObject(READ_BY_ID, new Object[]{roleId}, roleRowMapper);
    }

    @Override
    public void update(Role role) {
        jdbcTemplate.update(UPDATE, role.getName(), role.getId());
    }

    @Override
    public void delete(UUID roleId) {
        jdbcTemplate.update(DELETE, roleId);
    }


    @Override
    public boolean roleExists(UUID id) {
        return jdbcTemplate.query(SELECT_WHERE_ID, new Object[]{id}, roleRowMapper).size() == 1;
    }


}