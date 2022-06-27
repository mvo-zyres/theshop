package com.onlineshop.theshop.shop.db.impl;

import com.onlineshop.theshop.shop.db.dao.CartDAO;
import com.onlineshop.theshop.shop.db.dao.OrderDAO;
import com.onlineshop.theshop.shop.db.dao.StoreDAO;
import com.onlineshop.theshop.shop.db.dao.UserDAO;
import com.onlineshop.theshop.shop.model.user.User;
import com.onlineshop.theshop.shop.db.mapper.UserRowMapper;
import com.onlineshop.theshop.shop.db.mapper.UserIdRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class JdbcUserDAOImpl implements UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRowMapper userRowMapper;

    @Autowired
    CartDAO cartDAO;

    @Autowired
    StoreDAO storeDAO;

    @Autowired
    OrderDAO orderDAO;

    @Autowired
    UserIdRowMapper userIdRowMapper;


    private static final String NAME_EXISTS = "SELECT COUNT(*) FROM \"user\" WHERE user_name=?";
    private static final String CREATE = "INSERT INTO \"user\"(user_id , user_name, firstname, lastname, email, password, img, enabled, credits) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String READ = "SELECT user_id , user_name, firstname, lastname, email, password, img, enabled, credits FROM \"user\" ORDER BY user_name";
    private static final String READ_BY_ID = "SELECT user_id , user_name, firstname, lastname, email, password, img, enabled, credits FROM \"user\" WHERE user_id = ?";
    private static final String UPDATE = "UPDATE \"user\" SET user_name = ?, firstname = ?, lastname = ?, email = ?, password = ?, img = ?, enabled = ?, credits = ? WHERE user_id = ?";
    private static final String DELETE = "DELETE FROM \"user\" WHERE user_id = ?";


    @Override
    public boolean nameExists(String username) {
        return jdbcTemplate.queryForObject(NAME_EXISTS, new Object[]{username}, Integer.class)==1;
    }

    @Override
    public void create(User user) {
        jdbcTemplate.update(CREATE, user.getId(),
                user.getUsername(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getPassword(),
                user.getImg(),
                user.isEnabled(),
                user.getCredits());
    }

    @Override
    public List<User> read() {
        return jdbcTemplate.query(READ, userRowMapper);
    }

    @Override
    public User readById(UUID userId) {
        return jdbcTemplate.queryForObject(READ_BY_ID, new Object[]{userId}, userRowMapper);
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update(UPDATE,
                user.getUsername(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getPassword(),
                user.getImg(),
                user.isEnabled(),
                user.getCredits(),
                user.getId());
    }

    @Override
    public void delete(UUID userId) {
        jdbcTemplate.update(DELETE, userId);
    }
}
