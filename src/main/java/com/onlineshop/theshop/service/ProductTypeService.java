package com.onlineshop.theshop.service;

import com.onlineshop.theshop.shop.model.store.ProductType;
import com.onlineshop.theshop.shop.db.dao.ProductDAO;
import com.onlineshop.theshop.shop.db.dao.ProductTypeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ProductTypeService {

    @Autowired
    ProductTypeDAO productTypeDAO;

    @Autowired
    ProductDAO productDAO;

    @Autowired
    ProductService productService;

    public void addProductType(ProductType productType) {
        productTypeDAO.create(productType);
    }
    public void updateProductType(ProductType productType) {
        productTypeDAO.update(productType);
    }
    public ProductType getProductTypeById(UUID productTypeId) {
        return productTypeDAO.readById(productTypeId);
    }
    public List<ProductType> getAllProductTypes() {
        return productTypeDAO.read();
    }
    public ProductType getProductTypeByProductId(UUID productId) {
        return productTypeDAO.readById(productDAO.readTypeIdByProductId(productId));
    }
    public void deleteProductTypeById(UUID productTypeId) {
        List<UUID> productIds = productDAO.readProductIdsByTypeId(productTypeId);
        productIds.forEach(productId -> productService.deleteProduct(productId));
        productTypeDAO.delete(productTypeId);
    }

    public boolean productTypeExists(UUID productTypeId) {
        List<ProductType> productTypeList = productTypeDAO.read().stream().filter(productType -> productType.getId().toString().equals(productTypeId.toString())).collect(Collectors.toList());
        return productTypeList.size() == 1;
    }
}
