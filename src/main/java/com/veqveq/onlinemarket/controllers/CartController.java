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
    private Cart getCart(@RequestParam(name = "uuid") String cartId) {
        return cartService.getCart(formUUID(cartId));
    }

    @PostMapping("/add/{productId}")
    private void addOrder(@PathVariable Long productId, @RequestParam(name = "uuid") String cartId) {
        cartService.addToCart(formUUID(cartId), productService.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException("Product by id: " + productId + " not found")));
    }

    @PostMapping("/clean")
    private void cleanCart(@RequestParam(name = "uuid") String cartId) {
        cartService.cleanCart(formUUID(cartId));
    }

    @PostMapping("/delete/{productId}")
    private void deleteProductById(@PathVariable Long productId,
                                   @RequestParam(name = "uuid") String cartId) {
        cartService.removeInCart(formUUID(cartId), productService.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException("Product by id: " + productId + " not found")));
    }

    @PostMapping("/dec/{productId}")
    private void decrementProduct(@PathVariable Long productId,
                                  @RequestParam(name = "uuid") String cartId) {
        cartService.decInCart(formUUID(cartId), productService.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException("Product by id: " + productId + " not found")));
    }

    private UUID formUUID(String uuidToString){
        uuidToString = uuidToString.replace("\"","");
        return UUID.fromString(uuidToString);
    }
}
