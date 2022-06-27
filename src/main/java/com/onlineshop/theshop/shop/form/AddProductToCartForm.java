package com.onlineshop.theshop.shop.form;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class AddProductToCartForm {

    @NotNull
    private UUID productId;

    private int amount = 1;

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
