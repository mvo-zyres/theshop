package com.onlineshop.theshop.shop.form.user;

import java.util.UUID;

public class DeleteBillingForm {
    UUID delbuserId;
    String dbilling;

    public UUID getDelbuserId() {
        return delbuserId;
    }

    public void setDelbuserId(UUID delbuserId) {
        this.delbuserId = delbuserId;
    }

    public String getDbilling() {
        return dbilling;
    }

    public void setDbilling(String dbilling) {
        this.dbilling = dbilling;
    }
}
