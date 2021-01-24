package com.veqveq.onlinemarket.dto;

import com.veqveq.onlinemarket.models.Order;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private ProductDto product;
    private int count;

    public OrderDto(Order order) {
        this.id = order.getId();
        this.product = new ProductDto(order.getProduct());
        this.count = order.getCount();
    }
}
