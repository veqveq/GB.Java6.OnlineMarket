package com.veqveq.onlinemarket.dto;

import com.veqveq.onlinemarket.models.Order;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderDto {
    private Long id;
    private List<OrderItemDto> orderItems;
    private int totalPrice;
    private String createdAt;

    public OrderDto(Order order) {
        this.id = order.getId();
        this.totalPrice = order.getTotalPrice();
        this.orderItems = order.getOrderItems().stream().map(OrderItemDto::new).collect(Collectors.toList());
        this.createdAt = order.getCreatedAt().toLocalDate().toString();
    }
}
