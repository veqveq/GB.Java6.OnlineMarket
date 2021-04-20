package com.veqveq.onlinemarket.facades;

import com.veqveq.onlinemarket.models.Cart;
import com.veqveq.onlinemarket.models.User;
import com.veqveq.onlinemarket.services.CartService;
import com.veqveq.onlinemarket.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

public interface CartFacade {
    UUID createCart(Principal principal, UUID cartId);
}

@Service
@RequiredArgsConstructor
class CartFacadeService implements CartFacade{
    public final CartService cartService;
    public final UserService userService;

    @Override
    @Transactional
    public UUID createCart(Principal principal, UUID cartId) {
        User user = null;
        Cart cart = null;

        if (principal != null) user = userService.findByUsername(principal.getName()).orElse(null);
        if (cartId != null) cart = cartService.getCart(cartId);

        if (user == null && cart == null) return cartService.createNewCart().getId();

        if (user == null && cart != null) return cartId;

        if (user != null && cart == null) {
            Optional<Cart> currentUserCart = cartService.getUserCart(user);
            if (currentUserCart.isPresent()) {
                return currentUserCart.get().getId();
            } else {
                return cartService.createNewCart(user).getId();
            }
        }
        if (user != null && cart != null) {
            Optional<Cart> currentUserCart = cartService.getUserCart(user);
            if (currentUserCart.isPresent()) {
                if (!currentUserCart.get().getId().equals(cartId)) {
                    cart.merge(currentUserCart.get());
                    cartService.deleteCart(currentUserCart.get());
                    cart.setUser(user);
                }
                return cartId;
            }
            cart.setUser(user);
        }
        return cartId;
    }
}
