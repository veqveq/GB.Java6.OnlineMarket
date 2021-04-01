package com.veqveq.onlinemarket.dto;

import com.veqveq.onlinemarket.models.CartItem;
<<<<<<< HEAD

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
=======
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
>>>>>>> update_cart
    }
}
