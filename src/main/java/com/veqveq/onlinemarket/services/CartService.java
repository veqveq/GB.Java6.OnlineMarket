package com.veqveq.onlinemarket.services;

import com.veqveq.onlinemarket.exceptions.ResourceNotFoundException;
import com.veqveq.onlinemarket.models.Cart;
import com.veqveq.onlinemarket.models.CartItem;
import com.veqveq.onlinemarket.models.Product;
import com.veqveq.onlinemarket.repositories.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    public void createCart(Cart cart) {
        cartRepository.save(cart);
    }

    public Cart getCart(UUID cartId) {
        return findCart(cartId);
    }

    public void addToCart(UUID cartId, Product product) {
        Cart currentCart = findCart(cartId);
        currentCart.findItem(product).ifPresentOrElse(
                CartItem::incCount,
                () -> currentCart.addItem(new CartItem(product))
        );
        currentCart.recalculate();
    }

    public void decInCart(UUID cartId, Product product) {
        Cart currentCart = findCart(cartId);
        CartItem currentItem = currentCart.findItem(product).orElseThrow(() -> new ResourceNotFoundException("Product " + product.getTitle() + " not found in cart"));
        if (currentItem.getCount() == 1) {
            currentCart.removeItem(product);
        } else {
            currentItem.decCount();
        }
        currentCart.recalculate();
    }

    public void removeInCart(UUID cartId, Product product) {
        Cart currentCart = findCart(cartId);
        currentCart.removeItem(product);
        currentCart.recalculate();
    }

    public void cleanCart(UUID cartId) {
        Cart currentCart = findCart(cartId);
        currentCart.cleanCart();
    }

    private Cart findCart(UUID cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart by id: " + cartId + " not found"));
    }
}
