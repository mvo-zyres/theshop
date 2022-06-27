package com.onlineshop.theshop.service;

import com.onlineshop.theshop.shop.model.store.Cart;
import com.onlineshop.theshop.shop.model.store.Order;
import com.onlineshop.theshop.shop.model.user.Role;
import com.onlineshop.theshop.shop.model.user.User;
import com.onlineshop.theshop.shop.db.dao.CartDAO;
import com.onlineshop.theshop.shop.db.dao.OrderDAO;
import com.onlineshop.theshop.shop.db.dao.UserDAO;
import com.onlineshop.theshop.shop.db.dao.UserRoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    CartDAO cartDAO;

    @Autowired
    OrderDAO orderDAO;

    @Autowired
    RoleService roleService;

    @Autowired
    OrderService orderService;

    @Autowired
    CartService cartService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    OrderStatusService orderStatusService;

    @Autowired
    UserRoleDAO userRoleDAO;

    @Autowired
    ShippingAddressService shippingAddressService;

    @Autowired
    BillingAddressService billingAddressService;


    public void loadRole(User user) {
        user.setRole(roleService.getByUserId(user.getId()));
    }

    public Role loadRoleForPrivileges(User user){
        return roleService.getByUserId(user.getId());
    }

    public void loadCart(User user) {
        user.setCart(cartDAO.readByUserId(user.getId()));
        if (user.getCart() == null) {
            cartService.addNewCart(user.getId());
        }
    }

    public void loadOrders(User user){
        List<Order> orderList = new ArrayList<>();
        orderDAO.readByUserId(user.getId())
                .forEach(order -> {
                    orderService.loadOrderItems(order);
                    orderService.loadOrderStatuses(order);
                    orderList.add(order);
                });
        user.setOrders(orderList);
    }

    public void loadShippingAddresses(User user) {
        user.setShippingAddresses(shippingAddressService.getShippingAddressesByUserId(user.getId()));
    }

    public void loadDefaultShippingAddress(User user) {
        user.setDefaultShippingAddress(shippingAddressService.getDefaultShippingAddressByUserId(user.getId()));
    }
    public void loadDefaultBillingAddress(User user) {
        user.setDefaultBillingAddress(billingAddressService.getDefaultBillingAddressByUserId(user.getId()));
    }
    public void loadBillingAddresses(User user) {
        user.setBillingAddresses(billingAddressService.getBillingAddressesByUserId(user.getId()));
    }
    public User getUserById(UUID userId) {
        return userDAO.readById(userId);
    }

    public void updateUser(User user, UUID roleId) {
        userDAO.update(user);
        Role oldRole = roleService.getRoleById(userRoleDAO.readRoleIdByUserId(user.getId()));
        userRoleDAO.delete(user.getId(), oldRole.getId());
        userRoleDAO.create(user.getId(), roleId);
    }

    public void deleteUser(UUID userId) {
        Cart cart = cartService.getCartByUserId(userId);
        cartService.deleteAllProductsFromCartByCartId(cart.getId());
        cartDAO.delete(cart.getId());
        List<Order> orderList = orderDAO.readByUserId(userId);
        orderList.forEach(order -> {
            orderItemService.deleteOrderItemsByOrderId(order.getId());
            orderStatusService.deleteOrderStatusesByOrderId(order.getId());
            orderService.deleteOrder(order.getId());
        });
        shippingAddressService.setDefaultShippingAddress(null, userId);
        shippingAddressService.getShippingAddressesByUserId(userId).forEach(address -> shippingAddressService.deleteShippingAddress(address.getAddressId()));
        billingAddressService.setDefaultBillingAddress(null, userId);
        billingAddressService.getBillingAddressesByUserId(userId).forEach(address -> billingAddressService.deleteBillingAddress(address.getAddressId()));
        roleService.removeRoleFromUserByUserId(userId);
        userDAO.delete(userId);
    }

    public List<User> getAllUser() {
        List<User> userList = userDAO.read();
        userList.forEach(user -> {
            loadRole(user);
            loadOrders(user);
            loadCart(user);
            loadShippingAddresses(user);
            loadBillingAddresses(user);
            loadDefaultShippingAddress(user);
            loadDefaultBillingAddress(user);
        });
        return userList;
    }

    public void addUser(User user, UUID roleId) {
        userDAO.create(user);
        cartService.addNewCart(user.getId());
        userRoleDAO.create(user.getId(), roleId);
        if (user.getDefaultShippingAddress() != null) {
            shippingAddressService.addShippingAddress(user.getDefaultShippingAddress());
            shippingAddressService.setDefaultShippingAddress(user.getDefaultShippingAddress().getAddressId(), user.getId());
        }
        if (user.getDefaultBillingAddress() != null) {
            billingAddressService.addBillingAddress(user.getDefaultBillingAddress());
            billingAddressService.setDefaultBillingAddress(user.getDefaultBillingAddress().getAddressId(), user.getId());
        }
    }

    public boolean nameExists(String name) {
        List<User> userList = userDAO.read().stream().filter(user -> user.getUsername().equals(name)).collect(Collectors.toList());
        return userList.size() == 1;
    }

    public boolean emailExists(String email) {
        List<User> userList = userDAO.read().stream().filter(user -> user.getEmail().equals(email)).collect(Collectors.toList());
        return userList.size() == 1;
    }
    public boolean userExists(UUID userId) {
        List<User> userList = userDAO.read().stream().filter(user -> user.getId().toString().equals(userId.toString())).collect(Collectors.toList());
        return userList.size() == 1;
    }

    public void loadAddresses(User user) {
        loadShippingAddresses(user);
        loadBillingAddresses(user);
        loadDefaultShippingAddress(user);
        loadDefaultBillingAddress(user);
    }
}
