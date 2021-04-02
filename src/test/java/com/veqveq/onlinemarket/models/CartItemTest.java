package com.veqveq.onlinemarket.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {CartItem.class, Product.class})
public class CartItemTest {

    private final Product product;

    public CartItemTest() {
        this.product = new Product();
        product.setCost(100);
        product.setTitle("Test");
    }

    @Test
    public void initializationTest(){
        CartItem cartItem = new CartItem(product);
        Assertions.assertEquals(1, cartItem.getCount());
    }

    @Test
    public void decTest() {
        CartItem cartItem = new CartItem(product);
        cartItem.dec();
        Assertions.assertEquals(0, cartItem.getCount());
    }

    @Test
    public void incTest() {
        CartItem cartItem = new CartItem(product);
        cartItem.inc();
        Assertions.assertEquals(2, cartItem.getCount());
    }
}
