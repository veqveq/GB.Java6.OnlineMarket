package com.veqveq.onlinemarket.controllers;

import com.veqveq.onlinemarket.dto.CartDto;
import com.veqveq.onlinemarket.exceptions.ResourceNotFoundException;
import com.veqveq.onlinemarket.models.Product;
import com.veqveq.onlinemarket.models.User;
import com.veqveq.onlinemarket.services.CartService;
import com.veqveq.onlinemarket.services.ProductService;
import com.veqveq.onlinemarket.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final ProductService productService;

    @PostMapping
    public UUID createCart(@RequestParam(required = false) UUID cartId,
                           Principal principal) {
        return cartService.createCart(principal, cartId);
    }

    @PostMapping("/get")
    public CartDto getCart(@RequestParam UUID cartId) {
        return new CartDto(cartService.getCart(cartId));
    }

    @PostMapping("/add")
    @Transactional
    public void addOrIncItem(@RequestParam UUID cartId, @RequestParam Long productId) {
        Product product = productService.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product by id :" + productId + " not found"));
        cartService.addItem(cartId, product);
    }

    @PostMapping("/del")
    public void removeItem(@RequestParam UUID cartId, @RequestParam Long productId) {
        cartService.removeItem(cartId, productId);
    }

    @PostMapping("/dec")
    public void decItem(@RequestParam UUID cartId, @RequestParam Long productId) {
        cartService.decItemCount(cartId, productId);
    }

    @PostMapping("/clean")
    public void cleanCart(@RequestParam UUID cartId) {
        cartService.cleanCart(cartId);
    }
}