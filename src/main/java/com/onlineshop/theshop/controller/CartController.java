package com.onlineshop.theshop.controller;

import com.onlineshop.theshop.service.CartService;
import com.onlineshop.theshop.service.ShopSessionService;
import com.onlineshop.theshop.shop.model.store.Cart;
import com.onlineshop.theshop.shop.model.store.ShopSession;
import com.onlineshop.theshop.shop.model.user.User;
import com.onlineshop.theshop.CONSTANT;
import com.onlineshop.theshop.shop.form.AddProductToCartForm;
import com.onlineshop.theshop.shop.form.RemoveProductFromCartForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    ShopSessionService shopSessionService;

    @GetMapping(value = {"/", ""})
    public String cart(HttpServletRequest request) {
        return "redirect:/cart/detail?id="+ shopSessionService.getShopSession(request).getUser().getCart().getId();
    }


    @PostMapping("/addProduct.post")
    public String addProductPost(AddProductToCartForm addProductToCartForm, HttpServletRequest request){
        User currentUser = shopSessionService.getShopSession(request).getUser();
        int amount = addProductToCartForm.getAmount();
        List<UUID> productIds = new ArrayList<>();
        if (addProductToCartForm.getProductId()!= null) {
            for (int i = 0; i<amount; i++) {
                productIds.add(addProductToCartForm.getProductId());
            }
            cartService.loadProducts(currentUser.getCart());
            cartService.addProductsToCart(productIds, currentUser.getCart().getId());
            cartService.loadProducts(currentUser.getCart());
            return "redirect:/cart";
        }
        else {
            return CONSTANT.REDIRECT_400;
        }
    }

    @GetMapping("/detail")
    public String detailCart(Model model, HttpServletRequest request) {
        ShopSession shopSession = shopSessionService.getShopSession(request);
        User currentUser = shopSession.getUser();
        cartService.loadProducts(currentUser.getCart());
        Cart cart = currentUser.getCart();
        cartService.loadProducts(cart);
        model.addAttribute("products", cart.getProducts());
        model.addAttribute("cart", cart);
        return "cart/detail";
    }

    @PostMapping("/removeProduct.post")
    public String removeProductPost(RemoveProductFromCartForm removeProductFromCartForm, HttpServletRequest request) {
        boolean worked = cartService.removeProductFromCart(removeProductFromCartForm.getProductId(), request);
        if (worked) {
            return "redirect:/cart";
        }
        else {
            return CONSTANT.REDIRECT_500;
        }
    }

}
