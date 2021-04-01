package com.veqveq.onlinemarket.dto;

import com.veqveq.onlinemarket.models.Cart;
import lombok.Data;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class CartDto {
    private UUID id;
    private List<CartItemDto> cartItems;
    private int cartPrice;

    public CartDto(Cart cart) {
        this.id = cart.getId();
        this.cartPrice = cart.getCartPrice();
        this.cartItems = cart.getCartItems().stream()
                .map(CartItemDto::new)
                .collect(Collectors.toList());
    }
}
