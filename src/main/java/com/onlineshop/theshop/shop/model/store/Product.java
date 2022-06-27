package com.onlineshop.theshop.shop.model.store;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Product implements Serializable {

    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private UUID img;
    private List<UUID> images;
    private ProductType productType;
    private List<String> tags;
    private List<Category> categories;
    private boolean recommended;
    private BigDecimal tax = BigDecimal.ZERO;

    public Product(boolean randomUUID) {
        if(randomUUID) {
            this.id = UUID.randomUUID();
        }
    }

    public Product(UUID productID, String name, String description, BigDecimal price, UUID img, BigDecimal tax) {
        this.id = productID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.img = img;
        this.tax = tax;
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

    public List<UUID> getImages() {
        return images;
    }

    public void setImages(List<UUID> images) {
        this.images = images;
    }

    public List<String> getTags() {
        return tags;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Category> getCategories() {
        return categories;
    }
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public boolean isRecommended() {
        return recommended;
    }
    public ProductType getProductType() {
        return productType;
    }

    public void setRecommended(boolean recommended){
            this.recommended = recommended;
        }
    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

}
