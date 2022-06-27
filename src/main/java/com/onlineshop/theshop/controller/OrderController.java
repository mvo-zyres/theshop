package com.onlineshop.theshop.controller;

import com.onlineshop.theshop.service.*;
import com.onlineshop.theshop.shop.form.order.AddOrderForm;
import com.onlineshop.theshop.shop.form.order.AddOrderStatusForm;
import com.onlineshop.theshop.shop.form.order.DeleteOrderForm;
import com.onlineshop.theshop.shop.model.store.Order;
import com.onlineshop.theshop.shop.model.store.OrderStatus;
import com.onlineshop.theshop.shop.model.user.Privilege;
import com.onlineshop.theshop.shop.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class OrderController {

    @Autowired
    ConvertService convertService;

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @Autowired
    RoleService roleService;

    @Autowired
    CartService cartService;

    @Autowired
    StoreService storeService;

    @Autowired
    ShopSessionService shopSessionService;

    @Autowired
    BillingAddressService billingAddressService;

    @Autowired
    ShippingAddressService shippingAddressService;

    private static final String TOTAL_PRICE = "totalPrice";
    private static final String ORDER = "order";
    private static final String NEWEST_ORDER_STATUS = "newestOrderStatus";
    private static final String OLDEST_ORDER_STATUS = "oldestOrderStatus";
    private static final String NEWEST_TIME = "newestTime";
    private static final String OLDEST_TIME = "oldestTime";
    private static final String USER = "user";
    private static final String ORDER_ITEMS = "orderItems";

    @GetMapping("/order/checkout")
    public String checkout(Model model, HttpServletRequest request) {
        User currentUser = shopSessionService.getShopSession(request).getUser();
        userService.loadCart(currentUser);
        cartService.loadProducts(currentUser.getCart());
        BigDecimal totalPrice = cartService.calcTotal(currentUser);
        userService.loadBillingAddresses(currentUser);
        userService.loadShippingAddresses(currentUser);
        model.addAttribute(USER, currentUser);
        model.addAttribute(TOTAL_PRICE, totalPrice);
        model.addAttribute("billinglist", currentUser.getBillingAddresses());
        model.addAttribute("shippinglist", currentUser.getShippingAddresses());
        return "order/checkout";
    }

    @PostMapping("/order/checkout.post")
    public String checkoutPost(HttpServletRequest request, AddOrderForm addOrderForm) {
        User currentUser = shopSessionService.getShopSession(request).getUser();
        currentUser.getCart().setUser(currentUser);
        Order order = convertService.cartToOrder(currentUser.getCart(), billingAddressService.getBillingAddressById(addOrderForm.getBilling()), shippingAddressService.getShippingAddressById(addOrderForm.getShipping()));
        orderService.addOrder(order);
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        BigDecimal totalPrice = cartService.calcTotal(currentUser);
        currentUser.setCredits(currentUser.getCredits().subtract(totalPrice));
        currentUser.setOrders(orders);
        userService.loadRole(currentUser);
        userService.loadBillingAddresses(currentUser);
        userService.loadShippingAddresses(currentUser);
        userService.updateUser(currentUser, currentUser.getRole().getId());
        cartService.deleteAllProductsFromCartByCartId(currentUser.getCart().getId());
        currentUser.getCart().setProducts(null);
        return "redirect:/order/checkoutSuccess";

    }

    @GetMapping("/order/checkoutSuccess")
    public String checkoutSuccess() {
        return "order/checkoutSuccess";
    }

    @GetMapping("/order/all")
    public String allOrders(Model model, HttpServletRequest request) {
        User currentUser = shopSessionService.getShopSession(request).getUser();
        userService.loadOrders(currentUser);
        for (Order order : currentUser.getOrders()) {
            orderService.loadOrderStatuses(order);
        }
        HashMap<Order, OrderStatus> orderOrderStatusMap = new HashMap<>();
        for (Order order : currentUser.getOrders()) {
            orderOrderStatusMap.put(order, getNewestOrderStatus(order.getOrderStatuses()));
        }
        model.addAttribute("orderWithOrderStatus", orderOrderStatusMap);
        return "order/all";
    }

    @GetMapping(value = {"/order/", "/order"})
    public String orderDetail(@RequestParam(name = "id", required = false) UUID orderId, Model model, HttpServletRequest request) {
        UUID storeManagementPrivilege = UUID.fromString("9f215b6c-a9fd-4462-a088-d13fa0b368ec");
        User currentUser = shopSessionService.getShopSession(request).getUser();
        userService.loadOrders(currentUser);
        userService.loadRole(currentUser);
        roleService.loadPrivileges(currentUser.getRole());
        Order order = orderService.getOrderById(orderId);
        if (orderId != null) {
            orderService.loadOrderItems(order);
            orderService.loadOrderStatuses(order);
            orderService.loadAddress(order);
            if (currentUser.getRole().getPrivileges().stream().map(Privilege::getId).collect(Collectors.toList()).contains(storeManagementPrivilege) || currentUser.getOrders().stream().map(Order::getId).collect(Collectors.toList()).contains(order.getId())) {
                model.addAttribute(ORDER, order);
                model.addAttribute(ORDER_ITEMS, order.getOrderItems());
                model.addAttribute(NEWEST_ORDER_STATUS, getNewestOrderStatus(order.getOrderStatuses()));
                model.addAttribute(OLDEST_ORDER_STATUS, getOldestOrderStatus(order.getOrderStatuses()));
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                model.addAttribute(NEWEST_TIME, dateTimeFormatter.format(((OrderStatus) Objects.requireNonNull(model.getAttribute(NEWEST_ORDER_STATUS))).getTime().toLocalDateTime()));
                model.addAttribute(OLDEST_TIME, dateTimeFormatter.format(((OrderStatus) Objects.requireNonNull(model.getAttribute(OLDEST_ORDER_STATUS))).getTime().toLocalDateTime()));
                BigDecimal totalPrice = orderService.calcTotal(order);
                model.addAttribute(TOTAL_PRICE, totalPrice);
                return "order/detail";
            }
        }
        return "redirect:/order/all";
    }

    public OrderStatus getNewestOrderStatus(List<OrderStatus> orderStatuses) {
        OrderStatus newest = orderStatuses.get(0);
        for(OrderStatus orderStatus : orderStatuses){
            if(orderStatus.getTime().after(newest.getTime()))
                newest = orderStatus;
        }
        return newest;
    }
    public OrderStatus getOldestOrderStatus(List<OrderStatus> orderStatuses) {
        OrderStatus oldest = orderStatuses.get(0);
        for(OrderStatus orderStatus : orderStatuses){
            if(orderStatus.getTime().before(oldest.getTime()))
                oldest = orderStatus;
        }
        return oldest;
    }



    @GetMapping("/management/order/detail")
    public String detailOrder(@RequestParam(name = "id") UUID orderId, Model model) {
        model.addAttribute("allStores", storeService.getAllStores());
        model.addAttribute(ORDER, orderService.getOrderById(orderId));
        return "/order/detailOrder";
    }

    @GetMapping("/management/order/addOrderStatus")
    public String addOrderStatus(@RequestParam(name = "orderId") UUID orderId,Model model) {
        model.addAttribute(ORDER, orderService.getOrderById(orderId));
        return "/order/addOrderStatus";
    }

    @PostMapping("/management/order/addOrderStatus.post")
    public String editOrderPost(AddOrderStatusForm addOrderStatusForm) {
        Order order = orderService.getOrderById(addOrderStatusForm.getOrderId());
        orderService.addOrderStatus(new OrderStatus(addOrderStatusForm.getName(), order));
        return "redirect:/management/order?id="+order.getId();
    }
    @GetMapping("/management/order/addOrderStatusSuccess")
    public String addOrderStatusSuccess() {
        return "/order/addOrderStatusSuccess";
    }



    @GetMapping("/management/order/delete")
    public String deleteOrder(@RequestParam(name = "id") UUID orderId,Model model) {
        model.addAttribute(ORDER, orderService.getOrderById(orderId));
        return "/order/deleteOrder";
    }
    @PostMapping("/management/order/delete.post")
    public String deleteOrderPost(DeleteOrderForm deleteOrderForm) {
        if (deleteOrderForm.getOrderId() != null) {
            orderService.deleteOrder(deleteOrderForm.getOrderId());
        }
        return "redirect:/management/order/all";
    }
    @GetMapping("/management/order/deleteOrderSuccess")
    public String deleteOrderSuccess() {
        return "/order/deleteOrderSuccess";
    }


    @GetMapping("/management/order/all")
    public String allOrders(Model model) {
        model.addAttribute("allOrders", orderService.getAllOrders());
        return "/order/allOrders";
    }

}
