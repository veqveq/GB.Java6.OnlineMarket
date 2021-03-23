package com.veqveq.onlinemarket.dto;

import com.veqveq.onlinemarket.models.CartItem;

public class CartItemDto {
    private Long productId;
    private String productTitle;
    private int count;
    private int costPerProduct;
    private int totalCost;

    public CartItemDto(CartItem cartItem) {
        this.productId = cartItem.getProduct().getId();
        this.productTitle = cartItem.getProduct().getTitle();
        this.count = cartItem.getCount();
        this.costPerProduct = cartItem.getCostPerProduct();
        this.totalCost = costPerProduct * count;
    }
}
