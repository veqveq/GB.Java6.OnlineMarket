package com.veqveq.onlinemarket.controllers;

import com.veqveq.onlinemarket.dto.CartDto;
import com.veqveq.onlinemarket.exceptions.ResourceNotFoundException;
<<<<<<< HEAD
import com.veqveq.onlinemarket.models.Cart;
=======
import com.veqveq.onlinemarket.models.CartItem;
import com.veqveq.onlinemarket.models.Product;
>>>>>>> update_cart
import com.veqveq.onlinemarket.services.CartService;
import com.veqveq.onlinemarket.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final ProductService productService;

<<<<<<< HEAD
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
=======
    @PostMapping
    public UUID createCart() {
        return cartService.createCart();
    }

    @PostMapping("/get")
    public CartDto getCart(@RequestParam UUID cartId) {
        return new CartDto(cartService.getCart(cartId));
    }

    @PostMapping("/add")
    @Transactional
    public void addOrIncItem(@RequestParam UUID cartId, @RequestParam Long productId) {
        CartItem currentCartItem = cartService.getCart(cartId).findItem(productId);
        if (currentCartItem == null) {
            Product product = productService.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Product by id :" + productId + " not found"));
            cartService.addItem(cartId, product);
        } else {
            cartService.incItemCount(cartId, productId);
        }
    }

    @PostMapping("/del")
    public void removeItem(@RequestParam UUID cartId, @RequestParam Long productId) {
        cartService.removeItem(cartId, productId);
    }

    @PostMapping("/dec")
    public void decItem(@RequestParam UUID cartId, @RequestParam Long productId) {
        cartService.decItemCount(cartId, productId);
>>>>>>> update_cart
    }

    @PostMapping("/clean")
    public void cleanCart(@RequestParam UUID cartId) {
        cartService.cleanCart(cartId);
    }
}