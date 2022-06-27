package com.onlineshop.theshop.service;

import com.onlineshop.theshop.shop.model.user.Address;
import com.onlineshop.theshop.shop.db.dao.BillingAddressDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BillingAddressService {

    @Autowired
    UserService userService;

    @Autowired
    BillingAddressDAO billingAddressDAO;

    public void addBillingAddress(Address address) {
        billingAddressDAO.create(address);
    }

    public List<Address> getAllBillingAddresses() {
        return billingAddressDAO.read();
    }
    public Address getBillingAddressById(UUID addressId) {
        return billingAddressDAO.readByAddressId(addressId);
    }
    public List<Address> getBillingAddressesByUserId(UUID userId) {
        return billingAddressDAO.readByUserId(userId);
    }
    public void updateBillingAddress(Address address) {
        billingAddressDAO.update(address);
    }
    public void deleteBillingAddress(UUID addressId) {
        billingAddressDAO.delete(addressId);
    }
    public Address getDefaultBillingAddressByUserId(UUID userId) {
        return billingAddressDAO.readByAddressId(billingAddressDAO.readDefaultBillingAddressIdByUserId(userId));
    }
    public void setDefaultBillingAddress(UUID addressId, UUID userId) {
        billingAddressDAO.setDefaultBillingAddress(addressId, userId);
    }
    public void loadUser(Address address) {
        address.setUser(userService.getUserById(billingAddressDAO.readUserIdByAddressId(address.getAddressId())));
    }

    public boolean billingAddressExists(UUID addressId) {
        List<Address> addressList = billingAddressDAO.read().stream().filter(address -> address.getAddressId().toString().equals(addressId.toString())).collect(Collectors.toList());
        return addressList.size() == 1;
    }
}
