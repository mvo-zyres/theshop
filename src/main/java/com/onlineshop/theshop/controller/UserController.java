package com.onlineshop.theshop.controller;

import com.onlineshop.theshop.CONSTANT;
import com.onlineshop.theshop.service.*;
import com.onlineshop.theshop.shop.form.address.*;
import com.onlineshop.theshop.shop.form.user.AddUserForm;
import com.onlineshop.theshop.shop.form.user.DeleteUserForm;
import com.onlineshop.theshop.shop.form.user.EditUserForm;
import com.onlineshop.theshop.shop.model.store.ShopSession;
import com.onlineshop.theshop.shop.model.user.Address;
import com.onlineshop.theshop.shop.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    RoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @Autowired
    ShippingAddressService shippingAddressService;

    @Autowired
    BillingAddressService billingAddressService;

    @Autowired
    ShopSessionService shopSessionService;

    @GetMapping("/delete")
    public String deleteUser(@RequestParam(name = "id", required = false) UUID id, Model model) {
        if (id != null) {
            if (userService.userExists(id)) {
                User user = userService.getUserById(id);
                userService.loadCart(user);
                userService.loadOrders(user);
                userService.loadRole(user);
                model.addAttribute("duser", user);
                return "user/deleteuser";
            }
            else {
                return CONSTANT.REDIRECT_404;
            }

        }
        else {
            return CONSTANT.REDIRECT_400;
        }
    }
    @PostMapping("/delete.post")
    public String deleteUserPost(HttpServletRequest request, @Valid DeleteUserForm deleteUserForm){
        ShopSession shopSession = shopSessionService.getShopSession(request);
        User currentUser = shopSession.getUser();
        if (deleteUserForm.getUserId() != null) {
            if (userService.userExists(deleteUserForm.getUserId())) {
                if (!deleteUserForm.getUserId().equals(currentUser.getId())){
                    userService.deleteUser(deleteUserForm.getUserId());
                    return "redirect:/home";
                }
                else {
                    userService.deleteUser(deleteUserForm.getUserId());
                    ((UsernamePasswordAuthenticationToken) request.getUserPrincipal()).setDetails(null);
                    ((UsernamePasswordAuthenticationToken) request.getUserPrincipal()).setAuthenticated(false);

                    return "redirect:/logout";
                }
            }
            else {
                return CONSTANT.REDIRECT_404;
            }
        }
        else {
            return CONSTANT.REDIRECT_400;
        }

    }
    @PostMapping("/addAddress")
    public String addAddressToUserPost(AddUserForm addUserForm, Model model) {
        model.addAttribute("data", addUserForm);
        return "/user/addUserAddress";
    }


    @PostMapping("/add.post")
    public String addUserPost(HttpServletRequest request, AddUserForm addUserForm){
        if (!userService.nameExists(addUserForm.getName()) && !userService.emailExists(addUserForm.getEmail())) {
            if (addUserForm.getImg() == null) {
                addUserForm.setImg("https://cdn.pixabay.com/photo/2016/11/14/17/39/person-1824144_960_720.png");
            }
            if (addUserForm.getName() != null
                    && addUserForm.getFirstname() != null
                    && addUserForm.getLastname() != null
                    && addUserForm.getEmail() != null
                    && addUserForm.getPassword() != null
                    && addUserForm.getRoleId() != null
                    && addUserForm.getAddressLine1() != null
                    && addUserForm.getAddressCity() != null
                    && addUserForm.getAddressZipOrPostcode() != null
                    && addUserForm.getAddressCountryProvince() != null
                    && addUserForm.getAddressCountry() != null) {
                User addUser = new User(
                        UUID.randomUUID(),
                        addUserForm.getName(),
                        addUserForm.getFirstname(),
                        addUserForm.getLastname(),
                        addUserForm.getEmail(),
                        passwordEncoder.encode(addUserForm.getPassword()),
                        addUserForm.getImg(),
                        addUserForm.isEnabled(),
                        BigDecimal.ZERO);
                List<Address> billingAddresses = new ArrayList<>();
                List<Address> shippingAddresses = new ArrayList<>();
                Address address = new Address(addUser, addUserForm.getFirstname(), addUserForm.getLastname(), addUserForm.getAddressLine1(), addUserForm.getAddressLine2(), addUserForm.getAddressLine3(), addUserForm.getAddressCity(), addUserForm.getAddressZipOrPostcode(), addUserForm.getAddressCountryProvince(), addUserForm.getAddressCountry());
                billingAddresses.add(address);
                shippingAddresses.add(address);
                addUser.setShippingAddresses(shippingAddresses);
                addUser.setBillingAddresses(billingAddresses);
                addUser.setDefaultBillingAddress(address);
                addUser.setDefaultShippingAddress(address);
                userService.addUser(addUser, addUserForm.getRoleId());
                return "redirect:/success";
            }
        }
        return CONSTANT.REDIRECT_400;

    }

    @PostMapping("/edit.post")
    public String editUser(EditUserForm editUserForm, HttpServletRequest request){
        if (editUserForm.getId() != null) {
            if (userService.userExists(editUserForm.getId())) {
                User user = userService.getUserById(editUserForm.getId());
                userService.loadCart(user);
                userService.loadOrders(user);
                userService.loadRole(user);
                if (!Objects.equals(user.getUsername(), editUserForm.getUsername()) ||
                        !Objects.equals(user.getFirstname(), editUserForm.getFirstname()) ||
                        !Objects.equals(user.getLastname(), editUserForm.getLastname()) ||
                        !Objects.equals(user.getEmail(), editUserForm.getEmail()) ||
                        !Objects.equals(user.getImg(), editUserForm.getImg()) ||
                        user.getRole().getId() != editUserForm.getRoleId() ||
                        user.isEnabled() != editUserForm.isEnabled())
                {
                    user.setUsername(editUserForm.getUsername());
                    user.setFirstname(editUserForm.getFirstname());
                    user.setLastname(editUserForm.getLastname());
                    user.setEmail(editUserForm.getEmail());
                    user.setImg(editUserForm.getImg());
                    user.setEnabled(editUserForm.isEnabled());
                    userService.updateUser(user, editUserForm.getRoleId());
                    if(editUserForm.getId().equals(shopSessionService.getShopSession(request).getUser().getId()))
                        shopSessionService.getShopSession(request).setUser(user);
                    return "redirect:/success";
                }
                else {
                    return CONSTANT.REDIRECT_400;
                }
            }
            else {
                return CONSTANT.REDIRECT_404;
            }
        }
        else {
            return CONSTANT.REDIRECT_400;
        }

    }

    @GetMapping(value = {"/", ""})
    public String user(HttpServletRequest request, @RequestParam(name = "id", required = false) UUID id, Model model){
        if (id != null) {
            if (userService.userExists(id)) {
                User user = userService.getUserById(id);
                userService.loadCart(user);
                userService.loadOrders(user);
                userService.loadRole(user);
                model.addAttribute("duser", user);
                model.addAttribute("allroles", roleService.getAllRoles());
                return "user/user";
            }
            else {
                return CONSTANT.REDIRECT_404;
            }
        }
        else {
            return CONSTANT.REDIRECT_400;
        }


    }
    @GetMapping("/add")
    public String addUser(Model model){
        model.addAttribute("roles", roleService.getAllRoles());
        return "user/addUser";
    }

    @GetMapping("/all")
    public String allUser(Model model) {
        model.addAttribute("allUsers", userService.getAllUser());
        return "/user/allUsers";
    }




    @PostMapping("/billingAddress/add.post")
    public String addBillingAddressPost(AddBillingAddressForm addBillingAddressForm) {
        Address address = new Address();
        address.setAddressId(UUID.randomUUID());
        address.setFirstname(addBillingAddressForm.getFirstname());
        address.setLastname(addBillingAddressForm.getLastname());
        address.setLine1(addBillingAddressForm.getLine1());
        address.setLine2(addBillingAddressForm.getLine2());
        address.setLine3(addBillingAddressForm.getLine3());
        address.setCity(addBillingAddressForm.getCity());
        address.setZipOrPostcode(addBillingAddressForm.getZipOrPostcode());
        address.setCountryProvince(addBillingAddressForm.getCountryProvince());
        address.setCountry(addBillingAddressForm.getCountry());
        address.setUser(userService.getUserById(addBillingAddressForm.getUserId()));
        billingAddressService.addBillingAddress(address);
        return "redirect:/user/billingAddress/addSuccess";
    }
    @PostMapping("/shippingAddress/add.post")
    public String addShippingAddressPost(AddShippingAddressForm addShippingAddressForm) {
        Address address = new Address();
        address.setAddressId(UUID.randomUUID());
        address.setFirstname(addShippingAddressForm.getFirstname());
        address.setLastname(addShippingAddressForm.getLastname());
        address.setLine1(addShippingAddressForm.getLine1());
        address.setLine2(addShippingAddressForm.getLine2());
        address.setLine3(addShippingAddressForm.getLine3());
        address.setCity(addShippingAddressForm.getCity());
        address.setZipOrPostcode(addShippingAddressForm.getZipOrPostcode());
        address.setCountryProvince(addShippingAddressForm.getCountryProvince());
        address.setCountry(addShippingAddressForm.getCountry());
        address.setUser(userService.getUserById(addShippingAddressForm.getUserId()));
        shippingAddressService.addShippingAddress(address);
        return "redirect:/user/shippingAddress/addSuccess";
    }

    @GetMapping("/billingAddress/addSuccess")
    public String addBillingAddressSuccess() {
       return "/address/addBillingAddressSuccess";
    }
    @GetMapping("/shippingAddress/addSuccess")
    public String addShippingAddressSuccess() {
        return "/address/addShippingAddressSuccess";
    }

    @GetMapping("/billingAddress/editSuccess")
    public String editBillingAddressSuccess() {
        return "/address/editBillingAddressSuccess";
    }
    @GetMapping("/shippingAddress/editSuccess")
    public String editShippingAddressSuccess() {
        return "/address/editShippingAddressSuccess";
    }

    @GetMapping("/billingAddress/deleteSuccess")
    public String deleteBillingAddressSuccess() {
        return "/address/deleteBillingAddressSuccess";
    }
    @GetMapping("/shippingAddress/deleteSuccess")
    public String deleteShippingAddressSuccess() {
        return "/address/deleteShippingAddressSuccess";
    }

    @PostMapping("/billingAddress/edit.post")
    public String editBillingAddressPost(EditBillingAddressForm editBillingAddressForm) {
        Address address = new Address(editBillingAddressForm.getAddressId(),
                editBillingAddressForm.getFirstname(),
                editBillingAddressForm.getLastname(),
                editBillingAddressForm.getLine1(),
                editBillingAddressForm.getLine2(),
                editBillingAddressForm.getLine3(),
                editBillingAddressForm.getCity(),
                editBillingAddressForm.getZipOrPostcode(),
                editBillingAddressForm.getCountryProvince(),
                editBillingAddressForm.getCountry());
        billingAddressService.updateBillingAddress(address);
        return "redirect:/user/billingAddress/editSuccess";
    }
    @PostMapping("/shippingAddress/edit.post")
    public String editShippingAddressPost(EditShippingAddressForm editShippingAddressForm) {
        Address address = new Address(editShippingAddressForm.getAddressId(),
                editShippingAddressForm.getFirstname(),
                editShippingAddressForm.getLastname(),
                editShippingAddressForm.getLine1(),
                editShippingAddressForm.getLine2(),
                editShippingAddressForm.getLine3(),
                editShippingAddressForm.getCity(),
                editShippingAddressForm.getZipOrPostcode(),
                editShippingAddressForm.getCountryProvince(),
                editShippingAddressForm.getCountry());
        shippingAddressService.updateShippingAddress(address);
        return "redirect:/user/shippingAddress/editSuccess";
    }

    @PostMapping("/shippingAddress/delete.post")
    public String deleteShippingAddressPost(DeleteShippingAddressForm deleteShippingAddressForm) {
        Address address = shippingAddressService.getShippingAddressById(deleteShippingAddressForm.getAddressId());
        shippingAddressService.loadUser(address);
        if (shippingAddressService.getDefaultShippingAddressByUserId(address.getUser().getId()).getAddressId() != address.getAddressId()) {
            shippingAddressService.deleteShippingAddress(deleteShippingAddressForm.getAddressId());
            return "redirect:/user/shippingAddress/deleteSuccess";
        }
        else {
            return CONSTANT.REDIRECT_403;
        }
    }

    @PostMapping("/billingAddress/delete.post")
    public String deleteBillingAddressPost(DeleteBillingAddressForm deleteBillingAddressForm) {
        Address address = billingAddressService.getBillingAddressById(deleteBillingAddressForm.getAddressId());
        billingAddressService.loadUser(address);
        if (billingAddressService.getDefaultBillingAddressByUserId(address.getUser().getId()).getAddressId() != address.getAddressId()) {
            billingAddressService.deleteBillingAddress(deleteBillingAddressForm.getAddressId());
            return "redirect:/user/billingAddress/deleteSuccess";
        }
        else {
            return CONSTANT.REDIRECT_403;
        }
    }




}
