package com.veqveq.onlinemarket.dto;

import com.veqveq.onlinemarket.models.Order;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDto {
    private Long productId;
    private String productTitle;
    private int count;
    private int costPerProduct;
    private int totalCost;

    public OrderDto(Order order) {
        this.productId = order.getProduct().getId();
        this.productTitle = order.getProduct().getTitle();
        this.count = order.getCount();
        this.costPerProduct = order.getCostPerProduct();
        this.totalCost = order.getTotalCost();
    }
}
