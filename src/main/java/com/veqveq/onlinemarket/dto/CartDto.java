package com.veqveq.onlinemarket.dto;

import com.veqveq.onlinemarket.models.Cart;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CartDto {
    private List<CartItemDto> cartItems;
    private int cartPrice;

    public CartDto(Cart cart) {
        this.cartItems = cart.getCartItems().stream().map(CartItemDto::new).collect(Collectors.toList());
        this.cartPrice = cart.getCartPrice();
    }
}
