package com.veqveq.onlinemarket.controllers;

import com.veqveq.onlinemarket.dto.CartDto;
import com.veqveq.onlinemarket.exceptions.ResourceNotFoundException;
import com.veqveq.onlinemarket.models.Cart;
import com.veqveq.onlinemarket.services.CartService;
import com.veqveq.onlinemarket.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;


@RestController
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final ProductService productService;

    @GetMapping
    private UUID createCart() {
        Cart newCart = new Cart();
        cartService.createCart(newCart);
        return newCart.getId();
    }

    @PostMapping("/get")
    private CartDto getCart(@RequestParam(name = "cartId") UUID cartId) {
        return new CartDto(cartService.getCart(cartId));
    }

    @PostMapping("/add/{productId}")
    private void addOrder(@PathVariable Long productId, @RequestParam(name = "uuid") String cartId) {
        UUID cart = UUID.fromString(cartId);
        cartService.addToCart(cart, productService.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException("Product by id: " + productId + " not found")));
    }

    @PostMapping("/clean")
    private void cleanCart(@RequestParam(name = "uuid") UUID cartId) {
        cartService.cleanCart(cartId);
    }

    @PostMapping("/delete/{productId}")
    private void deleteProductById(@PathVariable Long productId,
                                   @RequestParam(name = "uuid") UUID cartId) {
        cartService.removeInCart(cartId, productService.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException("Product by id: " + productId + " not found")));
    }

    @PostMapping("/dec/{productId}")
    private void decrementProduct(@PathVariable Long productId,
                                  @RequestParam(name = "uuid") UUID cartId) {
        cartService.decInCart(cartId, productService.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException("Product by id: " + productId + " not found")));
    }
}
