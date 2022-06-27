package com.onlineshop.theshop.shop.db.dao;

import com.onlineshop.theshop.shop.model.store.ProductType;

import java.util.List;
import java.util.UUID;


public interface ProductTypeDAO {


    void create(ProductType productType);
    List<ProductType> read();
    ProductType readById(UUID productTypeId);
    void update(ProductType productType);
    void delete(UUID productTypeId);


}
