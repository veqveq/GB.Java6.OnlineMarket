package com.veqveq.onlinemarket.controllers;

import com.veqveq.onlinemarket.dto.CartDto;
import com.veqveq.onlinemarket.beans.Cart;
import com.veqveq.onlinemarket.models.Order;
import com.veqveq.onlinemarket.models.User;
import com.veqveq.onlinemarket.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final Cart cart;
    private final UserService userService;

    @GetMapping
    private CartDto getCart() {
        return new CartDto(cart);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    private void makeOrder(Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User by name: [%s] not found", principal.getName())));
        cart.makeOrder(new Order(user));
    }

    @GetMapping("/add/{id}")
    private void addOrder(@PathVariable Long id) {
        cart.addProduct(id);
    }

    @GetMapping("/clean")
    private void cleanCart() {
        cart.clean();
    }

    @GetMapping("/delete/{id}")
    private void deleteProductById(@PathVariable Long id) {
        cart.deleteProduct(id);
    }

    @GetMapping("/dec/{id}")
    private void decrementProduct(@PathVariable Long id) {
        cart.decrementProduct(id);
    }
}
