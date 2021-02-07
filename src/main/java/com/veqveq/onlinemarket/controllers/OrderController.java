package com.veqveq.onlinemarket.controllers;

import com.veqveq.onlinemarket.dto.OrderDto;
import com.veqveq.onlinemarket.exceptions.ResourceNotFoundException;
import com.veqveq.onlinemarket.models.Order;
import com.veqveq.onlinemarket.models.User;
import com.veqveq.onlinemarket.services.OrderService;
import com.veqveq.onlinemarket.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final UserService userService;
    private final OrderService orderService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    private void makeOrder(Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() ->
                new ResourceNotFoundException(String.format("User by name: [%s] not found", principal.getName())));
        orderService.saveOrder(user);
    }

    @GetMapping
    private List<OrderDto> getOrdersByUsermane(Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() ->
                new ResourceNotFoundException(String.format("User by name: [%s] not found", principal.getName())));
        return orderService.findOrdersByUsername(user.getUsername());
    }
}
