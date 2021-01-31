package com.veqveq.onlinemarket.dto;

import com.veqveq.onlinemarket.models.OrderItem;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemDto {
    private Long productId;
    private String productTitle;
    private int count;
    private int costPerProduct;
    private int totalCost;

    public OrderItemDto(OrderItem order) {
        this.productId = order.getProduct().getId();
        this.productTitle = order.getProduct().getTitle();
        this.count = order.getCount();
        this.costPerProduct = order.getCostPerProduct();
        this.totalCost = order.getTotalCost();
    }
}
