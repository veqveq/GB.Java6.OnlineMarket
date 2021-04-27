package com.veqveq.onlinemarket.facades;

import com.veqveq.onlinemarket.factories.CartFactory;
import com.veqveq.onlinemarket.services.CartService;
import com.veqveq.onlinemarket.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.UUID;

public interface CartFacade {
    UUID createCart(Principal principal, UUID cartId);
}

@Service
@RequiredArgsConstructor
class CartFacadeService implements CartFacade {
    public final CartService cartService;
    public final UserService userService;
    public final CartFactory cartFactory;

    @Override
    @Transactional
    public UUID createCart(Principal principal, UUID cartId) {
        return cartFactory
                .setCart(cartService.getCartOpt(cartId).orElse(null))
                .setUser(userService.findByUsername(principal.getName()).orElse(null))
                .create()
                .getId();
    }
}
