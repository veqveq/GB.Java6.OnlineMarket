package com.veqveq.onlinemarket.dto;

import com.veqveq.onlinemarket.models.CartItem;
import lombok.Data;

@Data
public class CartItemDto {
    private Long id;
    private ProductDto product;
    private int count;
    private int cost;
    private int itemPrice;

    public CartItemDto(CartItem cartItem) {
        this.id = cartItem.getId();
        this.product = new ProductDto(cartItem.getProduct());
        this.count = cartItem.getCount();
        this.cost = cartItem.getCost();
        this.itemPrice = cartItem.getItemPrice();
    }
}
