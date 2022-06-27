package com.onlineshop.theshop.shop.db.impl;

import com.onlineshop.theshop.shop.db.dao.PrivilegeDAO;
import com.onlineshop.theshop.shop.model.user.Privilege;
import com.onlineshop.theshop.shop.db.mapper.PrivilegeRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class JdbcPrivilegeDAOImpl implements PrivilegeDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PrivilegeRowMapper privilegeRowMapper;

    private static final String SELECT_WHERE_ID = "SELECT privilege_id, name FROM privilege WHERE privilege_id = ?";
    private static final String CREATE = "INSERT INTO privilege (privilege_id, name) VALUES (?, ?)";
    private static final String READ = "SELECT privilege_id, name FROM privilege";
    private static final String READ_BY_ID = "SELECT privilege_id, name FROM privilege WHERE privilege_id = ?";
    private static final String UPDATE = "UPDATE privilege SET name = ? WHERE privilege_id = ?";
    private static final String DELETE = "DELETE FROM privilege WHERE privilege_id = ?";

    @Override
    public void create(Privilege privilege) {
        jdbcTemplate.update(CREATE, privilege.getId(), privilege.getName());
    }

    @Override
    public List<Privilege> read() {
        return jdbcTemplate.query(READ, privilegeRowMapper);
    }

    @Override
    public Privilege readById(UUID privilegeId) {
        return jdbcTemplate.queryForObject(READ_BY_ID, new Object[]{privilegeId}, privilegeRowMapper);
    }

    @Override
    public void update(Privilege privilege) {
        jdbcTemplate.update(UPDATE, privilege.getName(), privilege.getId());
    }

    @Override
    public void delete(UUID privilegeId) {
        jdbcTemplate.update(DELETE, privilegeId);
    }

    @Override
    public boolean privilegeExists(UUID id) {
        return jdbcTemplate.query(SELECT_WHERE_ID, new Object[]{id}, privilegeRowMapper).size() == 1;
    }

}