package com.onlineshop.theshop.service;

import com.onlineshop.theshop.shop.model.store.*;
import com.onlineshop.theshop.shop.model.user.Address;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ConvertService {

    public Order cartToOrder(Cart cart, Address billing, Address shipping) {
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setOrderNumber(order.getRandomOrderNumber());
        order.setUser(cart.getUser());
        order.setOrderItems(cart.getProducts().
                stream()
                .map(this::productToOrderItem)
                .collect(Collectors.toList()));
        List<OrderStatus> orderStatuses = new ArrayList<>();
        orderStatuses.add(new OrderStatus("Order Created", order));
        order.setOrderStatuses(orderStatuses);
        order.setBillingaddress(billing);
        order.setShippingaddress(shipping);
        return order;
    }
    public OrderItem productToOrderItem(Product product){
        OrderItem orderItem = new OrderItem();
        orderItem.setId(UUID.randomUUID());
        orderItem.setName(product.getName());
        orderItem.setDescription(product.getDescription());
        orderItem.setPrice(product.getPrice());
        orderItem.setImg(product.getImg());
        orderItem.setReferenceId(product.getId());
        orderItem.setTax(product.getTax());
        return orderItem;
    }
}
