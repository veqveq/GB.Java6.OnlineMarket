package com.veqveq.onlinemarket.services;

import com.veqveq.onlinemarket.exceptions.ResourceNotFoundException;
import com.veqveq.onlinemarket.models.Cart;
import com.veqveq.onlinemarket.models.CartItem;
import com.veqveq.onlinemarket.models.Product;
import com.veqveq.onlinemarket.repositories.CartRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest(classes = {CartService.class, Product.class, CartItem.class})
public class CartServiceTest {
    @Autowired
    private CartService cartService;

    @MockBean
    private CartRepository cartRepository;

    @Test
    public void getCartTest() {
        Cart cart = new Cart();
        CartItem ci = new CartItem();
        initMockBeanCart(cart);
        cart.getCartItems().add(ci);
        Assertions.assertEquals(1, cartService.getCart(cart.getId()).getCartItems().size());
        Mockito.verify(cartRepository, Mockito.times(1))
                .findById(ArgumentMatchers.eq(cart.getId()));
    }

    @Test
    public void addToCartTest() {
        Cart cart = new Cart();
        initMockBeanCart(cart);

        Product p = new Product();
        p.setId(1L);
        p.setCost(100);

        cartService.addItem(cart.getId(), p);
        Mockito.verify(cartRepository, Mockito.times(1))
                .findById(ArgumentMatchers.eq(cart.getId()));
        CartItem ci = cartService.getCart(cart.getId()).findItem(p.getId());
        Assertions.assertEquals(100, ci.getCost());

        cartService.addItem(cart.getId(), p);
        CartItem ci2 = cartService.getCart(cart.getId()).findItem(p.getId());
        Assertions.assertEquals(2, ci2.getCount());

        Product p2 = new Product();
        cartService.addItem(cart.getId(), p2);
        Assertions.assertEquals(2, cartService.getCart(cart.getId()).getCartItems().size());
    }

    @Test
    public void removeInCartTest() {
        Cart cart = new Cart();
        initMockBeanCart(cart);

        Product p = new Product();
        p.setId(1L);

        cartService.addItem(cart.getId(), p);
        cartService.removeItem(cart.getId(), p.getId());
        Mockito.verify(cartRepository, Mockito.times(2))
                .findById(ArgumentMatchers.eq(cart.getId()));
        Assertions.assertEquals(0, cartService.getCart(cart.getId()).getCartItems().size());
    }

    @Test
    public void decInCartTest() {
        Cart cart = new Cart();
        initMockBeanCart(cart);

        Product p = new Product();
        p.setId(1L);

        cartService.addItem(cart.getId(), p);
        cartService.addItem(cart.getId(), p);
        cartService.decItemCount(cart.getId(), p.getId());
        Assertions.assertEquals(1, cartService.getCart(cart.getId()).findItem(p.getId()).getCount());

        cartService.decItemCount(cart.getId(), p.getId());
        Assertions.assertThrows(ResourceNotFoundException.class,
                ()->cartService.decItemCount(cart.getId(),p.getId()));
    }

    @Test
    public void cleanCartTest(){
        Cart cart = new Cart();
        Product p = new Product();
        initMockBeanCart(cart);
        cartService.addItem(cart.getId(),p);
        cartService.cleanCart(cart.getId());
        Assertions.assertEquals(0,cartService.getCart(cart.getId()).getCartItems().size());
        Mockito.verify(cartRepository, Mockito.times(3))
                .findById(ArgumentMatchers.eq(cart.getId()));
    }

    private void initMockBeanCart(Cart cart) {
        Mockito
                .doReturn(Optional.of(cart))
                .when(cartRepository)
                .findById(cart.getId());
    }
}
