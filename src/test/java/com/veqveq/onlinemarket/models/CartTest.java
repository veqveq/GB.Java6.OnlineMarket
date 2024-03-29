package com.veqveq.onlinemarket.models;

import com.veqveq.onlinemarket.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest(classes = {Cart.class, CartItem.class, Product.class})
public class CartTest {

    @Test
    public void addItemTest() {
        Cart cart = new Cart();
        cart.add(new CartItem());
        Assertions.assertEquals(1, cart.getCartItems().size());
    }

    @Test
    public void removeItemTest() {
        Cart cart = new Cart();
        Product p1 = new Product();
        Product p2 = new Product();
        p1.setId(1L);
        p2.setId(2L);
        cart.add(new CartItem(p1));
        cart.add(new CartItem(p2));
        cart.remove(p1.getId());
        Assertions.assertEquals(1, cart.getCartItems().size());
        Assertions.assertThrows(ResourceNotFoundException.class,
                ()->cart.remove(p1.getId()));
    }

    @Test
    public void findItemTest() {
        Cart cart = new Cart();
        Product p1 = new Product();
        Product p2 = new Product();
        p1.setId(1L);
        p2.setId(2L);
        cart.add(new CartItem(p1));
        Assertions.assertEquals(1L, cart.findItem(p1.getId()).getProduct().getId());
        Assertions.assertEquals(null,cart.findItem(p2.getId()));
    }

    @Test
    public void recalculateTest() {
        Cart cart = new Cart();
        CartItem item1 = new CartItem();
        CartItem item2 = new CartItem();
        CartItem item3 = new CartItem();
        item1.setCost(10);
        item2.setCost(20);
        item3.setCost(30);
        item1.setCount(2);
        item2.setCount(3);
        item3.setCount(4);
        cart.add(item1);
        cart.add(item2);
        cart.add(item3);
        cart.recalculate();
        Assertions.assertEquals(200, cart.getCartPrice());
    }
}
