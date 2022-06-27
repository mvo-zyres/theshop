package com.onlineshop.theshop.shop.form.role;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public class AddRoleForm {

    @NotEmpty
    private String name;

    @NotNull
    private List<UUID> pIds;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UUID> getPIds() {
        return pIds;
    }

    public void setPIds(List<UUID> pIds) {
        this.pIds = pIds;
    }
}
