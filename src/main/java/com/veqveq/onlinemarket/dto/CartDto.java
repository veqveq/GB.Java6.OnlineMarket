package com.veqveq.onlinemarket.dto;

import com.veqveq.onlinemarket.beans.Cart;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CartDto {
    private List<OrderDto> orders;
    private int totalPrice;

    public CartDto(Cart cart) {
        this.orders = cart.getOrders().stream().map(OrderDto::new).collect(Collectors.toList());
        this.totalPrice = cart.getTotalPrice();
    }
}
