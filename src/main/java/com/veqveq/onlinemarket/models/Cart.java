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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
                .mapToInt(CartItem::getItemPrice)
                .reduce(Integer::sum).orElse(0);
    }

    public Optional<CartItem> findItem(Product product) {
        return cartItems.stream()
                .filter((ci) -> ci.getProduct().equals(product))
                .findFirst();
    }

    public void addItem(CartItem cartItem) {
        cartItems.add(cartItem);
    }

    public void removeItem(CartItem cartItem) {
        cartItems.remove(cartItem);
    }
}
