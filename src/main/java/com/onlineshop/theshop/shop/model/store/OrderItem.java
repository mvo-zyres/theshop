package com.onlineshop.theshop.shop.model.store;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public class OrderItem implements Serializable {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal tax;
    private UUID img;
    private UUID referenceId;
    private Order order;

    public OrderItem() {
    }

    public OrderItem(UUID id, String name, String description, BigDecimal price, UUID img, UUID referenceId, BigDecimal tax) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.tax = tax;
        this.img = img;
        this.referenceId = referenceId;
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

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public UUID getImg() {
        return img;
    }
    public void setImg(UUID img) {
        this.img = img;
    }

    public UUID getReferenceId() {
        return referenceId;
    }
    public void setReferenceId(UUID referenceId) {
        this.referenceId = referenceId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }
}
