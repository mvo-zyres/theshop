package com.onlineshop.theshop.shop.model.user;

import com.onlineshop.theshop.shop.model.store.Cart;
import com.onlineshop.theshop.shop.model.store.Order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class User implements Serializable {
    private UUID id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String img;
    private List<Address> shippingAddresses;
    private List<Address> billingAddresses;
    private Address defaultShippingAddress;
    private Address defaultBillingAddress;
    private boolean enabled;
    private Role role;
    private Cart cart;
    private List<Order> orders;
    private BigDecimal credits;


    public User(UUID id, String username, String firstname, String lastname, String email, String password, String img, boolean enabled, BigDecimal credits) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.img = img;
        this.enabled = enabled;
        this.credits = credits;
    }

    public User(UUID id, String username, String firstname, String lastname, String email, String password, String img, boolean enabled, BigDecimal credits, Address defaultBillingAddress, Address defaultShippingAddress, List<Address> billingAddresses, List<Address> shippingAddresses) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.img = img;
        this.enabled = enabled;
        this.credits = credits;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public List<Address> getShippingAddresses() {
        return shippingAddresses;
    }

    public void setShippingAddresses(List<Address> shippingAddresses) {
        this.shippingAddresses = shippingAddresses;
    }

    public List<Address> getBillingAddresses() {
        return billingAddresses;
    }

    public void setBillingAddresses(List<Address> billingAddresses) {
        this.billingAddresses = billingAddresses;
    }

    public Address getDefaultShippingAddress() {
        return defaultShippingAddress;
    }

    public void setDefaultShippingAddress(Address defaultShippingAddress) {
        this.defaultShippingAddress = defaultShippingAddress;
    }

    public Address getDefaultBillingAddress() {
        return defaultBillingAddress;
    }

    public void setDefaultBillingAddress(Address defaultBillingAddress) {
        this.defaultBillingAddress = defaultBillingAddress;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", img='" + img + '\'' +
                ", shippingAddresses=" + shippingAddresses +
                ", billingAddresses=" + billingAddresses +
                ", defaultShippingAddress=" + defaultShippingAddress +
                ", defaultBillingAddress=" + defaultBillingAddress +
                ", enabled=" + enabled +
                ", role=" + role +
                ", cart=" + cart +
                ", orders=" + orders +
                ", credits=" + credits +
                '}';
    }
}
