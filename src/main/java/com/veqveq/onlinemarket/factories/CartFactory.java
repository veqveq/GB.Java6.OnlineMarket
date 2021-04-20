package com.veqveq.onlinemarket.factories;

import com.veqveq.onlinemarket.models.Cart;
import com.veqveq.onlinemarket.models.User;
import com.veqveq.onlinemarket.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartFactory {
    private final CartService cartService;

    public Cart createCart(Cart cart, User user) {
        CartCreator cartCreator;
        if (cart == null && user == null) {
            cartCreator = new NewCartCreator(cartService);
        } else if (cart != null && user == null) {
            cartCreator = new CartReturner(cart);
        } else if (cart == null) {
            cartCreator = new NewCartByUserCreator(cartService, user);
        } else {
            cartCreator = new MergeCartCreator(cartService, user, cart);
        }
        return cartCreator.create();
    }
}

