package com.veqveq.onlinemarket.factories;

import com.veqveq.onlinemarket.models.Cart;
import com.veqveq.onlinemarket.models.User;
import com.veqveq.onlinemarket.services.CartService;

import java.util.Optional;

public interface CartCreator {
    Cart create();
}

class NewCartCreator implements CartCreator {
    private final CartService cartService;

    public NewCartCreator(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public Cart create() {
        return cartService.createNewCart();
    }
}

class CartReturner implements CartCreator {
    private final Cart cart;

    public CartReturner(Cart cart) {
        this.cart = cart;
    }

    @Override
    public Cart create() {
        return cart;
    }
}

class NewCartByUserCreator implements CartCreator {
    private final CartService cartService;
    private final User user;

    public NewCartByUserCreator(CartService cartService, User user) {
        this.cartService = cartService;
        this.user = user;
    }

    @Override
    public Cart create() {
        Optional<Cart> currentUserCart = cartService.getUserCart(user);
        return currentUserCart.orElseGet(() -> cartService.createNewCart(user));
    }
}

class MergeCartCreator implements CartCreator {

    private final CartService cartService;
    private final User user;
    private final Cart cart;

    public MergeCartCreator(CartService cartService, User user, Cart cart) {
        this.cartService = cartService;
        this.user = user;
        this.cart = cart;
    }

    @Override
    public Cart create() {
        Optional<Cart> currentUserCart = cartService.getUserCart(user);
        if (currentUserCart.isPresent()) {
            if (!currentUserCart.get().getId().equals(cart.getId())) {
                cart.merge(currentUserCart.get());
                cartService.deleteCart(currentUserCart.get());
            }
        }
        cart.setUser(user);
        return cart;
    }
}

