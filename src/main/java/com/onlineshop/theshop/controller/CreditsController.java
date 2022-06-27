package com.onlineshop.theshop.controller;

import com.onlineshop.theshop.service.ShopSessionService;
import com.onlineshop.theshop.service.UserService;
import com.onlineshop.theshop.shop.model.store.ShopSession;
import com.onlineshop.theshop.shop.model.user.User;
import com.onlineshop.theshop.CONSTANT;
import com.onlineshop.theshop.shop.form.RechargeCreditsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/credits")
public class CreditsController {

    @Autowired
    UserService userService;

    @Autowired
    ShopSessionService shopSessionService;

    @GetMapping("recharge")
    public String reChargeCredits() {
        return "credits/recharge";
    }

    @PostMapping("/recharge.post")
    public String reChargeCreditsPost(RechargeCreditsForm rechargeCreditsForm, HttpServletRequest request) {
        if (rechargeCreditsForm.getCredits() != null) {
            ShopSession shopSession = shopSessionService.getShopSession(request);
            User user = shopSession.getUser();
            userService.loadCart(user);
            userService.loadOrders(user);
            userService.loadRole(user);
            userService.loadAddresses(user);
            user.setCredits(user.getCredits().add(rechargeCreditsForm.getCredits()));
            userService.updateUser(user, user.getRole().getId());
            return "redirect:/credits/recharge";
        }
        else {
            return CONSTANT.REDIRECT_400;
        }
    }

}
