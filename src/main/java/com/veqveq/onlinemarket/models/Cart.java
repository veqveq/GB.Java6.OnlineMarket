package com.veqveq.onlinemarket.models;

import com.veqveq.onlinemarket.exceptions.ResourceNotFoundException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "carts_tbl")
@Data
public class Cart {
    @Id
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @GeneratedValue(generator = "generator")
    @Column(name = "id_fld")
    private UUID id;

    @Column(name = "cart_price_fld")
    private int cartPrice;

    @OneToMany(mappedBy = "cart")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<CartItem> cartItems;

    @Column(name = "created_at_fld")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at_fld")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Cart() {
        this.cartItems = new ArrayList<>();
    }

    public void recalculate() {
        cartPrice = cartItems.stream()
                .mapToInt((ci) -> ci.getCount() * ci.getCostPerProduct())
                .reduce(Integer::sum).orElse(0);
    }

    public Optional<CartItem> findItem(Product product) {
        return cartItems.stream()
                .filter((ci) -> ci.getProduct().equals(product))
                .findFirst();
    }

    public void addItem(CartItem cartItem, CartItem... cartItems) {
        this.cartItems.add(cartItem);
        if (cartItems.length != 0) {
            this.cartItems.addAll(Arrays.asList(cartItems));
        }
    }

    public void removeItem(Product product) {
        for (CartItem ci : cartItems) {
            if (ci.getProduct().equals(product)) {
                cartItems.remove(ci);
                return;
            }
        }
        throw new ResourceNotFoundException("Product " + product.getTitle() + " not found in cart");
    }

    public void cleanCart() {
        cartItems.clear();
        cartPrice = 0;
    }
}
