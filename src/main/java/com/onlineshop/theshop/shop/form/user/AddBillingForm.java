package com.onlineshop.theshop.shop.form.user;

import java.util.UUID;

public class AddBillingForm {
    UUID addbuserId;
    String abilling;

    public UUID getAddbuserId() {
        return addbuserId;
    }

    public void setAddbuserId(UUID addbuserId) {
        this.addbuserId = addbuserId;
    }

    public String getAbilling() {
        return abilling;
    }

    public void setAbilling(String abilling) {
        this.abilling = abilling;
    }
}
