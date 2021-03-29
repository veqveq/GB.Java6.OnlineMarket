package com.veqveq.onlinemarket.controllers;

import com.veqveq.onlinemarket.dto.CartDto;
import com.veqveq.onlinemarket.exceptions.ResourceNotFoundException;
import com.veqveq.onlinemarket.models.CartItem;
import com.veqveq.onlinemarket.models.Product;
import com.veqveq.onlinemarket.services.CartService;
import com.veqveq.onlinemarket.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final ProductService productService;

    @PostMapping
    public UUID createCart() {
        return cartService.createCart();
    }

    @PostMapping("/get")
    public CartDto getCart(@RequestBody UUID cartId) {
        return new CartDto(cartService.getCart(cartId));
    }

    @PostMapping("/add/{productId}")
    @Transactional
    public void addOrIncItem(@RequestBody UUID cartId, @PathVariable Long productId) {
        CartItem currentCartItem = cartService.getCart(cartId).findItem(productId);
        if (currentCartItem == null) {
            Product product = productService.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Product by id :" + productId + " not found"));
            cartService.addItem(cartId, product);
        } else {
            cartService.incItemCount(cartId, productId);
        }
    }

    @PostMapping("/remove/{productId}")
    public void removeItem(@RequestBody UUID cartId, @PathVariable Long productId) {
        cartService.removeItem(cartId, productId);
    }

    @PostMapping("/dec/{productId}")
    public void decItem(@RequestBody UUID cartId, @PathVariable Long productId) {
        cartService.decItemCount(cartId, productId);
    }
}