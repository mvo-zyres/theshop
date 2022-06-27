package com.onlineshop.theshop.service;

import com.onlineshop.theshop.shop.db.dao.*;
import com.onlineshop.theshop.shop.model.store.Cart;
import com.onlineshop.theshop.shop.model.store.Category;
import com.onlineshop.theshop.shop.model.store.Product;
import com.onlineshop.theshop.shop.model.store.Store;
import com.onlineshop.theshop.shop.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    ProductDAO productDAO;

    @Autowired
    StoreDAO storeDAO;

    @Autowired
    CartDAO cartDAO;

    @Autowired
    CartProductDAO cartProductDAO;

    @Autowired
    UserService userService;

    @Autowired
    ShopSessionService shopSessionService;

    @Autowired
    ProductService productService;


    public void loadUser(Cart cart){
        cart.setUser(
                userDAO.readById(
                        cartDAO.readUserIdByCartId(
                                cart.getId()
                        )
                )
        );
    }
    public void loadProducts(Cart cart){
        List<Product> productList = cartProductDAO.readByCartId(cart.getId())
        .stream()
        .map(uuid -> productDAO.readById(uuid))
        .collect(Collectors.toList());
        cart.setProducts(productList);
    }

    public void loadStore(Cart cart) {
        cart.setStore(storeDAO.readById(cartDAO.readStoreIdByCartId(cart.getId())));
    }


    public void addProductsToCart(List<UUID> productIds, UUID cartId) {
        Cart cart = new Cart(cartId, userDAO.readById(cartDAO.readUserIdByCartId(cartId)), cartProductDAO.readByCartId(cartId).stream().map(uuid -> productDAO.readById(uuid)).collect(Collectors.toList()), storeDAO.readById(cartDAO.readStoreIdByCartId(cartId)));
        productIds
                .forEach(uuid -> cart.getProducts().add(productDAO.readById(uuid)));
        productIds
                .forEach(productId -> cartProductDAO.create(cartId, productId));
    }

    public Cart getCartById(UUID cartId) {
        return new Cart(
                cartId,
                userDAO.readById(cartDAO.readUserIdByCartId(cartId)),
                cartProductDAO
                        .readByCartId(cartId)
                        .stream()
                        .map(uuid -> productDAO.readById(uuid))
                        .collect(Collectors.toList()),
                storeDAO.readById(cartDAO.readStoreIdByCartId(cartId))
        );
    }

    public void deleteAllProductsFromCartByCartId(UUID cartId) {
        Cart cart = getCartById(cartId);
        loadProducts(cart);
        cart.getProducts().forEach(product -> cartProductDAO.delete(cart.getId(), product.getId()));
    }

    public void addNewCart(UUID userId) {
        List<Store> stores = storeDAO.read();
        cartDAO.create(UUID.randomUUID(), userId, stores.get(0).getId());
    }

    public Cart getCartByUserId(UUID userId) {
        Cart cart = cartDAO.readByUserId(userId);
        return new Cart(
                cart.getId(),
                userDAO.readById(userId),
                cartProductDAO
                        .readByCartId(cart.getId())
                        .stream()
                        .map(uuid -> productDAO.readById(uuid))
                        .collect(Collectors.toList()),
                storeDAO.readById(cartDAO.readStoreIdByCartId(cart.getId()))
        );
    }

    public BigDecimal calcTotal(User currentUser){
        BigDecimal totalPrice = BigDecimal.ZERO;
        if (currentUser.getCart() != null && currentUser.getCart().getProducts() != null) {
            for (Product product : currentUser.getCart().getProducts()) {
                productService.loadCategories(product);
                Optional<Category> result = product.getCategories().stream().filter(category -> category.getTax()!=null).findFirst();
                if (product.getTax() != null) {
                    totalPrice = totalPrice.add(product.getPrice().multiply(calcTax(product.getTax())));
                } else if (result.isPresent()) {
                    Category category = result.get();
                    totalPrice = totalPrice.add(product.getPrice().multiply(calcTax(category.getTax())));
                } else {
                    totalPrice = totalPrice.add(product.getPrice().multiply(calcTax(currentUser.getCart().getStore().getTax())));
                }
            }
        }
        return totalPrice;
    }

    public BigDecimal calcTax(BigDecimal tax) {
       return BigDecimal.valueOf(100).add(tax).divide(BigDecimal.valueOf(100));
    }

    public boolean removeProductFromCart(UUID productId, HttpServletRequest request) {
        User user = shopSessionService.getShopSession(request).getUser();

        user.getCart().setProducts(user.getCart().getProducts().stream().filter(product -> !product.getId().toString().equals(productId.toString())).collect(Collectors.toList()));
        if (user.getCart().getProducts().stream().anyMatch(product -> product.getId().toString().equals(productId.toString()))) {
            return false;
        }
        else {
            cartProductDAO.delete(user.getCart().getId(), productId);
            return true;
        }
    }

    public boolean cartExists(UUID cartId) {
        List<Cart> cartList = cartDAO.read().stream().filter(cart -> cart.getId().toString().equals(cartId.toString())).collect(Collectors.toList());
        return cartList.size() == 1;
    }

    public void deleteCartByUserId(UUID userId) {
        cartDAO.delete(getCartByUserId(userId).getId());
    }
}
