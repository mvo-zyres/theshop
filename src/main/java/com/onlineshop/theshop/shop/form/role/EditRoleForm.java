package com.onlineshop.theshop.shop.form.role;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public class EditRoleForm {

    @NotEmpty
    private String roleName;

    @NotNull
    private UUID roleId;

    @NotNull
    private List<UUID> pIds;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }

    public List<UUID> getPIds() {
        return pIds;
    }

    public void setPIds(List<UUID> pIds) {
        this.pIds = pIds;
    }
}