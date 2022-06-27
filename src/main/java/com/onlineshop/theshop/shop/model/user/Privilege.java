package com.onlineshop.theshop.shop.model.user;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class Privilege implements Serializable {

    private UUID id;

    private String name;

    public Privilege(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
