package com.onlineshop.theshop.shop.form.order;

import java.util.UUID;

public class AddOrderForm {
    private UUID billing;

    private UUID shipping;

    private boolean same;

    public UUID getBilling() {
        return billing;
    }

    public void setBilling(UUID billing) {
        this.billing = billing;
    }

    public UUID getShipping() {
        if(same)
            return billing;
        return shipping;
    }

    public void setShipping(UUID shipping) {
        this.shipping = shipping;
    }

    public boolean isSame() {
        return same;
    }

    public void setSame(boolean same) {
        this.same = same;
    }
}
