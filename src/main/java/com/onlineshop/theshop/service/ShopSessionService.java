package com.onlineshop.theshop.service;

import com.onlineshop.theshop.shop.model.store.ShopSession;
import com.onlineshop.theshop.shop.model.user.User;
import com.onlineshop.theshop.shop.model.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Service
public class ShopSessionService {

    @Autowired
    JdbcUserDetailsService jdbcUserDetailsService;

    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;

    @Autowired
    RoleService roleService;

    private static final String SHOP_SESSION = "ShopSession";

    public ShopSession getShopSession(HttpServletRequest request){
        ShopSession shopSession = (ShopSession) request.getSession().getAttribute(SHOP_SESSION);
        if(shopSession == null) {
            shopSession = new ShopSession();
            HttpSession session = request.getSession();
            if (request.getUserPrincipal() != null) {
                UUID userId = ((UserPrincipal) ((UsernamePasswordAuthenticationToken) request.getUserPrincipal()).getPrincipal()).getUser().getId();
                User user = jdbcUserDetailsService.loadUserById(userId);
                shopSession.setUser(user);
            }
            session.setAttribute(SHOP_SESSION,shopSession);
        }
        return shopSession;
    }

    public void deleteShopSession(HttpServletRequest request){
        request.getSession().removeAttribute(SHOP_SESSION);
    }

}
