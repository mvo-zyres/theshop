package com.onlineshop.theshop.shop.model.user;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
public class Role implements Serializable {

    private UUID id;

    private String name;

    private List<Privilege> privileges;

    public Role(UUID id, String name) {
        this.id = id;
        this.name = name;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }

    public Role(UUID id, String name, List<Privilege> privileges) {
        this.id = id;
        this.name = name;
        this.privileges = privileges;
    }
}