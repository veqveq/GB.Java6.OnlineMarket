package com.veqveq.onlinemarket.controllers;

import com.veqveq.onlinemarket.dto.CartDto;
import com.veqveq.onlinemarket.beans.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final Cart cart;

    @GetMapping
    private CartDto getCart() {
        cart.calculateTotalPrice();
        return new CartDto(cart);
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
