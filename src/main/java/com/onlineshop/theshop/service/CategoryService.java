package com.onlineshop.theshop.service;


import com.onlineshop.theshop.shop.model.store.Category;
import com.onlineshop.theshop.shop.model.store.Product;
import com.onlineshop.theshop.shop.db.dao.CategoryDAO;
import com.onlineshop.theshop.shop.db.dao.CategoryProductDAO;
import com.onlineshop.theshop.shop.db.dao.ProductDAO;
import com.onlineshop.theshop.shop.db.dao.StoreDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    ProductDAO productDAO;

    @Autowired
    StoreDAO storeDAO;

    @Autowired
    CategoryProductDAO categoryProductDAO;

    @Autowired
    CategoryDAO categoryDAO;

    public void loadProducts(Category category) {
        List<Product> productList = categoryProductDAO.readByCategoryId(category.getId())
        .stream()
        .map(uuid -> productDAO.readById(uuid))
        .collect(Collectors.toList());
        category.setProducts(productList);
    }
    public void loadStore(Category category) {
        category.setStore(storeDAO.readById(categoryDAO.readStoreIdByCategoryId(category.getId())));
    }

    public List<Category> getAllCategories() {
        List<Category> categories = categoryDAO.read();
        categories.forEach(category -> {
            loadProducts(category);
            loadStore(category);
        });
        return categories;
    }

    public void updateCategory(Category category) {
        categoryDAO.update(category);
    }
    public void addCategory(Category category) {
        categoryDAO.create(category);
    }
    public Category getCategoryById(UUID categoryId) {
        Category category = categoryDAO.readById(categoryId);
        loadStore(category);
        loadProducts(category);
        return category;
    }
    public void deleteCategory(UUID categoryId) {
        categoryProductDAO.deleteByCategoryId(categoryId);
        categoryDAO.delete(categoryId);
    }
    public boolean categoryExists(UUID categoryId) {
        List<Category> categoryList = categoryDAO.read().stream().filter(category -> category.getId().toString().equals(categoryId.toString())).collect(Collectors.toList());
        return categoryList.size() == 1;
    }
    public boolean nameExists(String name) {
        List<Category> categoryList = categoryDAO.read().stream().filter(category -> category.getName().equals(name)).collect(Collectors.toList());
        return categoryList.size() == 1;
    }
    public Category getCategoryByUrl(String url) {
        Category category = categoryDAO.readByUrl(url);
        loadProducts(category);
        loadStore(category);
        return category;
    }

    public boolean urlExists(String url) {
        List<Category> categoryList = categoryDAO.read().stream().filter(category -> category.getUrl().equals(url)).collect(Collectors.toList());
        return categoryList.size() == 1;
    }
}
