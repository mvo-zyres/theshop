package com.onlineshop.theshop.controller;

import com.onlineshop.theshop.service.ProductService;
import com.onlineshop.theshop.service.RoleService;
import com.onlineshop.theshop.service.ShopSessionService;
import com.onlineshop.theshop.shop.form.user.AddUserForm;
import com.onlineshop.theshop.shop.model.store.Product;
import com.onlineshop.theshop.shop.model.user.User;
import com.onlineshop.theshop.shop.model.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    RoleService roleService;

    @Autowired
    ShopSessionService shopSessionService;

    @GetMapping(value = {"","/", "/home"})
    public String home(Model model, HttpServletRequest request){
        User currentUser = ((UserPrincipal) ((UsernamePasswordAuthenticationToken) request.getUserPrincipal()).getPrincipal()).getUser();
        model.addAttribute("user", currentUser);
        List<Product> recommended = productService.getRecommended();
        model.addAttribute("products", recommended);
        return "index";
    }

    @GetMapping("/success")
    public String success(){
        return "success";
    }
    @GetMapping("/managementSuccess")
    public String managementSuccess(){
        return "managementSuccess";
    }
    @GetMapping("/thanks")
    public String thanks(){
        return "thanks";
    }
    @GetMapping("/windowClose")
    public String windowClose() {
        return "windowClose";
    }

    @GetMapping("/management")
    public String management(){
        return "authorities/management";
    }


    @GetMapping("/signup")
    public String signUp(Model model){
        model.addAttribute("roleId", roleService.getRoleByName("ROLE_USER").getId());
        return "signup";
    }
    @PostMapping("/signupAddress")
    public String signUpAddress(AddUserForm addUserForm, Model model){
        model.addAttribute("data", addUserForm);
        return "signupAddress";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        shopSessionService.deleteShopSession(request);
        ((UsernamePasswordAuthenticationToken) request.getUserPrincipal()).setAuthenticated(false);
        ((UsernamePasswordAuthenticationToken) request.getUserPrincipal()).setDetails(null);
        session.invalidate();
        request.getSession().invalidate();
        return  "redirect:/login";
    }




















}
