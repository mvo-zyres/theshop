package com.onlineshop.theshop.shop.form.user;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class DeleteUserForm {

    @NotNull
    private UUID userId;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
