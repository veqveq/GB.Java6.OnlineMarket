package com.veqveq.onlinemarket.services;

import com.veqveq.onlinemarket.dto.OrderDto;
import com.veqveq.onlinemarket.models.Cart;
import com.veqveq.onlinemarket.models.Order;
import com.veqveq.onlinemarket.models.User;
import com.veqveq.onlinemarket.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public void saveOrder(User user, Cart cart, String address) {
        orderRepository.save(new Order(cart, user, address));
    }

    public List<OrderDto> findOrdersByUsername(String username) {
        return orderRepository.findAllByOwnerUsername(username).stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());
    }
}
