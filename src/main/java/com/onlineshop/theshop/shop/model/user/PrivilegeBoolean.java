package com.onlineshop.theshop.shop.model.user;

public class PrivilegeBoolean {
    private Privilege privilege;
    private boolean aBoolean;


    public PrivilegeBoolean(Privilege privilege, boolean aBoolean) {
        this.privilege = privilege;
        this.aBoolean = aBoolean;
    }

    public PrivilegeBoolean(Privilege privilege) {
        this.privilege = privilege;
    }

    public PrivilegeBoolean(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }

    public boolean isaBoolean() {
        return aBoolean;
    }

    public void setaBoolean(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }
}
