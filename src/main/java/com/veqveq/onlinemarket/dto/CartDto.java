package com.veqveq.onlinemarket.dto;

import com.veqveq.onlinemarket.beans.Cart;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CartDto {
    private List<OrderItemDto> orders;
    private int totalPrice;

    public CartDto(Cart cart) {
        this.orders = cart.getOrders().stream().map(OrderItemDto::new).collect(Collectors.toList());
        this.totalPrice = calculateTotalPrice();
    }

    private int calculateTotalPrice() {
        int sum = 0;
        for (OrderItemDto o : orders) {
            sum += o.getTotalCost();
        }
        return sum;
    }
}
