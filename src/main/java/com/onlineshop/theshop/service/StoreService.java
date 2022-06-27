package com.onlineshop.theshop.service;

import com.onlineshop.theshop.shop.db.dao.*;
import com.onlineshop.theshop.shop.model.store.Cart;
import com.onlineshop.theshop.shop.model.store.Category;
import com.onlineshop.theshop.shop.model.store.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StoreService {
    @Autowired
    CategoryDAO categoryDAO;

    @Autowired
    CartDAO cartDAO;

    @Autowired
    StoreDAO storeDAO;

    @Autowired
    CategoryProductDAO categoryProductDAO;

    @Autowired
    CartProductDAO cartProductDAO;

    @Autowired
    CartService cartService;

    public void loadCategories(Store store) {
        store.setCategories(categoryDAO.readByStoreId(store.getId()));
    }

    public void loadCarts(Store store) {
        store.setCarts(cartDAO.readByStoreId(store.getId()));
    }

    public List<Store> getAllStores() {
        List<Store> stores = storeDAO.read();
        stores.forEach(store -> {
            loadCategories(store);
            loadCarts(store);
        });
        return stores;
    }

    public Store getStoreById(UUID storeId) {
        Store store = storeDAO.readById(storeId);
        loadCategories(store);
        loadCarts(store);
        return store;
    }

    public void updateStore(Store store) {
        storeDAO.update(store);
    }
    public void deleteStore(UUID storeId) {
        List<Category> categories = categoryDAO.readByStoreId(storeId);
        categories.forEach(category -> {
            categoryProductDAO.deleteByCategoryId(category.getId());
            categoryDAO.delete(category.getId());
        });
        List<Cart> carts = cartDAO.readByStoreId(storeId);
        carts.forEach(cart -> {
            cartService.loadProducts(cart);
            cartService.loadUser(cart);
            cartService.loadStore(cart);
            cartService.deleteAllProductsFromCartByCartId(cart.getId());
            cartDAO.delete(cart.getId());
        });
        storeDAO.delete(storeId);
    }

    public void addStore(Store store) {
        storeDAO.create(store);
    }

    public boolean storeExists(UUID storeId) {
        List<Store> storeList = storeDAO.read().stream().filter(store -> store.getId().toString().equals(storeId.toString())).collect(Collectors.toList());
        return storeList.size() == 1;
    }
}
