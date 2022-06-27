package com.onlineshop.theshop.shop.model.user;

import java.io.Serializable;
import java.util.UUID;

public class Address implements Serializable {
    private UUID addressId;
    private User user;
    private String firstname;
    private String lastname;
    private String line1;
    private String line2;
    private String line3;
    private String city;
    private String zipOrPostcode;
    private String countryProvince;
    private String country;

    public Address() {
    }

    public Address(User user, String line1, String firstname, String lastname, String line2, String line3, String city, String zipOrPostcode, String countryProvince, String country) {
        this.addressId = UUID.randomUUID();
        this.user = user;
        this.firstname = firstname;
        this.lastname = lastname;
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.city = city;
        this.zipOrPostcode = zipOrPostcode;
        this.countryProvince = countryProvince;
        this.country = country;
    }

    public Address(UUID addressId, String firstname, String lastname, String line1, String line2, String line3, String city, String zipOrPostcode, String countryProvince, String country) {
        this.addressId = addressId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.city = city;
        this.zipOrPostcode = zipOrPostcode;
        this.countryProvince = countryProvince;
        this.country = country;
    }

    public UUID getAddressId() {
        return addressId;
    }

    public void setAddressId(UUID addressId) {
        this.addressId = addressId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine3() {
        return line3;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipOrPostcode() {
        return zipOrPostcode;
    }

    public void setZipOrPostcode(String zipOrPostcode) {
        this.zipOrPostcode = zipOrPostcode;
    }

    public String getCountryProvince() {
        return countryProvince;
    }

    public void setCountryProvince(String countryProvince) {
        this.countryProvince = countryProvince;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDisplay(){
        return firstname+" "+lastname+", "+line1+" "+line2+" "+line3+", "+city+" "+zipOrPostcode+", "+countryProvince+" "+country;
    }

}
