package com.onlineshop.theshop.service;

import com.onlineshop.theshop.shop.model.user.Address;
import com.onlineshop.theshop.shop.db.dao.ShippingAddressDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ShippingAddressService {

    @Autowired
    UserService userService;

    @Autowired
    ShippingAddressDAO shippingAddressDAO;

    public void addShippingAddress(Address address) {
        shippingAddressDAO.create(address);
    }

    public List<Address> getAllShippingAddresses() {
        return shippingAddressDAO.read();
    }
    public Address getShippingAddressById(UUID addressId) {
        return shippingAddressDAO.readByAddressId(addressId);
    }
    public List<Address> getShippingAddressesByUserId(UUID userId) {
        return shippingAddressDAO.readByUserId(userId);
    }
    public void updateShippingAddress(Address address) {
        shippingAddressDAO.update(address);
    }
    public void deleteShippingAddress(UUID addressId) {
        shippingAddressDAO.delete(addressId);
    }
    public Address getDefaultShippingAddressByUserId(UUID userId) {
        return shippingAddressDAO.readByAddressId(shippingAddressDAO.readDefaultShippingAddressIdByUserId(userId));
    }
    public void setDefaultShippingAddress(UUID addressId, UUID userId) {
        shippingAddressDAO.setDefaultShippingAddress(addressId, userId);
    }
    public void loadUser(Address address) {
        address.setUser(userService.getUserById(shippingAddressDAO.readUserIdByAddressId(address.getAddressId())));
    }

    public boolean shippingAddressExists(UUID addressId) {
        List<Address> addressList = shippingAddressDAO.read().stream().filter(address -> address.getAddressId().toString().equals(addressId.toString())).collect(Collectors.toList());
        return addressList.size() == 1;
    }
}
