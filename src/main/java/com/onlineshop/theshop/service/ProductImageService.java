package com.onlineshop.theshop.service;

import com.onlineshop.theshop.shop.db.dao.ProductImageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductImageService {

    @Autowired
    ProductImageDAO productImageDAO;

    public List<UUID> getImagesByProductId(UUID productId) {
        return productImageDAO.readByProductId(productId);
    }

    public void addImages(UUID productId, UUID imageId) {
        productImageDAO.create(productId, imageId);
    }
    public void deleteImageById(UUID imageId) {
        productImageDAO.deleteImageById(imageId);
    }
    public void deleteImagesByProductId(UUID productId) {
        getImagesByProductId(productId).forEach(this::deleteImageById);
    }
}
