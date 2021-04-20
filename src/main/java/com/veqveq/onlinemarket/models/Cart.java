package com.veqveq.onlinemarket.models;

import com.veqveq.onlinemarket.exceptions.ResourceNotFoundException;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "carts_tbl")
@Data
public class Cart {
    @Id
    @GenericGenerator(name = "generatorUUID", strategy = "uuid2")
    @GeneratedValue(generator = "generatorUUID")
    @Column(name = "id_fld")
    private UUID id;

    @Column(name = "cart_price_fld")
    private int cartPrice;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "owner_id_fld")
    private User user;

    public void add(CartItem cartItem) {
        CartItem currentItem = findItem(cartItem.getProduct().getId());
        if (currentItem != null) {
            currentItem.inc();
            return;
        }
        cartItems.add(cartItem);
        cartItem.setCart(this);
        recalculate();
    }

    //    @Transactional
    public void remove(Long productId) {
        for (CartItem ci : cartItems) {
            if (ci.getProduct().getId().equals(productId)) {
                ci.setCart(null);
                cartItems.remove(ci);
                return;
            }
        }
        throw new ResourceNotFoundException("Product by id: " + productId + " not found");
    }

    public CartItem findItem(Long productId) {
        return cartItems.stream()
                .filter((ci) -> ci.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public void recalculate() {
        cartItems.forEach(CartItem::recalculate);
        cartPrice = cartItems.stream()
                .mapToInt(CartItem::getItemPrice)
                .reduce(Integer::sum)
                .orElse(0);
    }

    public void merge(Cart cart) {
        cart.getCartItems().forEach(this::add);
    }
}
