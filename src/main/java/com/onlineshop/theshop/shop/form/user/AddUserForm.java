package com.onlineshop.theshop.shop.form.user;

import com.onlineshop.theshop.validpwd.ValidPassword;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class AddUserForm {

    @NotNull
    @NotEmpty
    @Length(min =2, max = 16)
    private String name;

    @NotNull
    @NotEmpty
    @Length(min =2, max = 20)
    private String firstname;

    @NotNull
    @NotEmpty
    @Length(min =2, max = 20)
    private String lastname;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotEmpty
    @ValidPassword
    private String password;

    @NotNull
    private UUID roleId;

    private boolean enabled = true;

    @NotNull
    @NotEmpty
    private String img;

    @NotNull
    @NotEmpty
    private String addressLine1;

    private String addressLine2;

    private String addressLine3;

    @NotNull
    @NotEmpty
    @Max(30)
    private String addressCity;

    @Max(20)
    private String addressZipOrPostcode;

    @Max(30)
    private String addressCountryProvince;

    @Max(30)
    private String addressCountry;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressZipOrPostcode() {
        return addressZipOrPostcode;
    }

    public void setAddressZipOrPostcode(String addressZipOrPostcode) {
        this.addressZipOrPostcode = addressZipOrPostcode;
    }

    public String getAddressCountryProvince() {
        return addressCountryProvince;
    }

    public void setAddressCountryProvince(String addressCountryProvince) {
        this.addressCountryProvince = addressCountryProvince;
    }

    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }
}
