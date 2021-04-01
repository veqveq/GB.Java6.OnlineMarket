package com.veqveq.onlinemarket.services;

import com.veqveq.onlinemarket.exceptions.ResourceNotFoundException;
import com.veqveq.onlinemarket.models.Cart;
import com.veqveq.onlinemarket.models.CartItem;
import com.veqveq.onlinemarket.models.Product;
import com.veqveq.onlinemarket.repositories.CartItemRepository;
import com.veqveq.onlinemarket.repositories.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {
    public final CartRepository cartRepository;
    public final CartItemRepository cartItemRepository;

    public UUID createCart() {
        Cart cart = new Cart();
        cartRepository.save(cart);
        return cart.getId();
    }

    public Cart getCart(UUID cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart by id :" + cartId + " not found"));
    }

    @Transactional
    public void addItem(UUID cartId, Product product) {
        Cart cart = getCart(cartId);
        cart.add(new CartItem(product));
        cart.recalculate();
    }

    @Transactional
    public void removeItem(UUID cartId, Long productId) {
        Cart cart = getCart(cartId);
        cart.remove(productId);
        cartItemRepository.removeByProduct_Id(productId);
        cart.recalculate();
    }

    @Transactional
    public void incItemCount(UUID cartId, Long productId) {
        Cart cart = getCart(cartId);
        cart.getCartItems().stream()
                .filter((ci) -> ci.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product by id :" + productId + " not found in cart"))
                .inc();
        cart.recalculate();
    }

    @Transactional
    public void decItemCount(UUID cartId, Long productId) {
        Cart cart = getCart(cartId);
        CartItem cartItem = cart.getCartItems().stream()
                .filter((ci) -> ci.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product by id :" + productId + " not found in cart"));
        if (cartItem.getCount() > 1) {
            cartItem.dec();
        } else {
            removeItem(cartId, productId);
        }
        cart.recalculate();
    }

    @Transactional
    public void cleanCart(UUID cartId) {
        Cart cart = getCart(cartId);
        cart.getCartItems().clear();
        cartItemRepository.removeAllByCart_Id(cartId);
    }
}
