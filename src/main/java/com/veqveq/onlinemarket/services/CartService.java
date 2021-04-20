package com.veqveq.onlinemarket.services;

import com.veqveq.onlinemarket.exceptions.ResourceNotFoundException;
import com.veqveq.onlinemarket.models.Cart;
import com.veqveq.onlinemarket.models.CartItem;
import com.veqveq.onlinemarket.models.Product;
import com.veqveq.onlinemarket.models.User;
import com.veqveq.onlinemarket.repositories.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {
    public final CartRepository cartRepository;
    public final UserService userService;

    @Transactional
    public UUID createCart(Principal principal, UUID cartId) {
        User user = null;
        Cart cart = null;

        if (principal != null) user = userService.findByUsername(principal.getName()).orElse(null);
        if (cartId != null) cart = cartRepository.findById(cartId).orElse(null);

        if (user == null && cart == null) return createNewCart().getId();

        if (user == null && cart != null) return cartId;

        if (user != null && cart == null) {
            Optional<Cart> currentUserCart = cartRepository.findByUserId(user.getId());
            if (currentUserCart.isPresent()) {
                return currentUserCart.get().getId();
            } else {
                return createNewCart(user).getId();
            }
        }
        if (user != null && cart != null) {
            Optional<Cart> currentUserCart = cartRepository.findByUserId(user.getId());
            if (currentUserCart.isPresent()) {
                if (!currentUserCart.get().getId().equals(cartId)) {
                    cart.merge(currentUserCart.get());
                    cartRepository.delete(currentUserCart.get());
                    cart.setUser(user);
                }
                return cartId;
            }
            cart.setUser(user);
        }
        return cartId;
    }

    private Cart createNewCart() {
        Cart cart = new Cart();
        cartRepository.save(cart);
        return cart;
    }

    private Cart createNewCart(User user) {
        Cart cart = new Cart();
        cartRepository.save(cart);
        cart.setUser(user);
        return cart;
    }

    public Cart getCart(UUID cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart by id :" + cartId + " not found"));
    }

    @Transactional
    public void addItem(UUID cartId, Product product) {
        Cart cart = getCart(cartId);
        if (cart.findItem(product.getId()) == null) {
            cart.add(new CartItem(product));
            cart.recalculate();
            return;
        }
        incItemCount(cartId, product.getId());
    }

    @Transactional
    public void removeItem(UUID cartId, Long productId) {
        Cart cart = getCart(cartId);
        cart.remove(productId);
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
        cart.getCartItems().forEach((ci) -> ci.setCart(null));
        cart.getCartItems().clear();
        cart.recalculate();
    }
}
