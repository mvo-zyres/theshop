package com.onlineshop.theshop.interceptor;

import com.onlineshop.theshop.configproperties.ConfigProperties;
import com.onlineshop.theshop.service.CategoryService;
import com.onlineshop.theshop.service.ShopSessionService;
import com.onlineshop.theshop.service.UserService;
import com.onlineshop.theshop.shop.model.store.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;


public class ShopInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ShopSessionService shopSessionService;

    @Autowired
    ConfigProperties configProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        HttpSession session = request.getSession();
        if (request.getUserPrincipal() != null) {
            userService.loadAddresses(shopSessionService.getShopSession(request).getUser());
            System.out.println(request.getMethod() + " - " + request.getRequestURI());
            System.out.println(modelAndView.toString());
            modelAndView.addObject("user", shopSessionService.getShopSession(request).getUser());
        }
        List<Category> categories = categoryService.getAllCategories();
        if (modelAndView != null) {
            String minioUrl = "http://" + configProperties.getMinio().getOuterHostname() + ":" + configProperties.getMinio().getPort() + "/" + configProperties.getMinio().getBucket() + "/";
            modelAndView.addObject("MinioURL", minioUrl);
            if (categories != null) {
                modelAndView.addObject("categories", categories);
            }
            String search = (String) session.getAttribute("searchvalue");
            if (search != null && !search.equals("")) {
                    Objects.requireNonNull(modelAndView).addObject("search", search);
            }
        }
    }
}
