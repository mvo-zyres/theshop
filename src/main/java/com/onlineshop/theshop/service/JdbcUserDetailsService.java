package com.onlineshop.theshop.service;


import com.onlineshop.theshop.shop.model.user.User;
import com.onlineshop.theshop.shop.model.user.UserPrincipal;
import com.onlineshop.theshop.shop.db.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class JdbcUserDetailsService implements UserDetailsService {


    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleService roleService;

    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> result = userDAO.read()
                .stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
        if(!result.isPresent())
            throw new UsernameNotFoundException(username);
        User user = result.get();
        if (user.getRole() == null) {
            userService.loadRole(user);
        }
        if (user.getRole().getPrivileges() == null)
        {
            roleService.loadPrivileges(user.getRole());
        }
        if (user.getCart() == null) {
            userService.loadCart(user);
        }
        if (user.getCart().getProducts() == null) {
            cartService.loadProducts(user.getCart());
        }
        if (user.getCart().getStore() == null) {
            cartService.loadStore(user.getCart());
        }
        if (user.getCart().getUser() == null) {
            cartService.loadUser(user.getCart());
        }
        return new UserPrincipal(user);
    }

    public User loadUserById(UUID uuid) {
        User user = userDAO.readById(uuid);
        userService.loadRole(user);
        if (user.getRole().getPrivileges() == null)
        {
            roleService.loadPrivileges(user.getRole());
        }
        if (user.getCart() == null) {
            userService.loadCart(user);
            if (user.getCart() == null) {
                cartService.addNewCart(user.getId());
            }
        }
        if (user.getCart().getProducts() == null) {
            cartService.loadProducts(user.getCart());
        }
        if (user.getCart().getStore() == null) {
            cartService.loadStore(user.getCart());
        }
        if (user.getCart().getUser() == null) {
            cartService.loadUser(user.getCart());
        }
        return user;
    }
}
