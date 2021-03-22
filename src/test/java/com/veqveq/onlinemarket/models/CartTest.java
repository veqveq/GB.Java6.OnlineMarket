package com.veqveq.onlinemarket.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {Cart.class, CartItem.class, Product.class})
public class CartTest {

    @Test
    public void recalculateTest() {
        Cart cart = new Cart();
        CartItem item1 = new CartItem();
        CartItem item2 = new CartItem();
        CartItem item3 = new CartItem();
        item1.setItemPrice(10);
        item2.setItemPrice(20);
        item3.setItemPrice(30);
        cart.getCartItems().add(item1);
        cart.getCartItems().add(item2);
        cart.getCartItems().add(item3);
        cart.recalculate();
        Assertions.assertEquals(60, cart.getCartPrice());
    }

    @Test
    public void findItemTest() {
        Product product = new Product();
        CartItem cartItem = new CartItem(product);
        Cart cart = new Cart();
        cart.getCartItems().add(cartItem);
        Assertions.assertEquals(cartItem, cart.findItem(product).get());
    }
}
