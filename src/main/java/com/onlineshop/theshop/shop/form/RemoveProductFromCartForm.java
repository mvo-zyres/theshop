package com.onlineshop.theshop.shop.form;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class RemoveProductFromCartForm {

    @NotNull
    private UUID productId;

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }
}
