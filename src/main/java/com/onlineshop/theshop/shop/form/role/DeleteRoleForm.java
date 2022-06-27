package com.onlineshop.theshop.shop.form.role;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class DeleteRoleForm {

    @NotNull
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
