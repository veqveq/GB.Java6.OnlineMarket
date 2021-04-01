package com.veqveq.onlinemarket.models;

import com.veqveq.onlinemarket.exceptions.ResourceNotFoundException;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> cartItems;

    public void add(CartItem cartItem) {
        cartItems.add(cartItem);
        cartItem.setCart(this);
        recalculate();
    }

    @Transactional
    public void remove(Long productId) {
        for (CartItem ci : cartItems) {
            if (ci.getProduct().getId().equals(productId)) {
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
        cartPrice = cartItems.stream()
                .mapToInt(CartItem::getItemPrice)
                .reduce(Integer::sum)
                .orElse(0);
    }
}
