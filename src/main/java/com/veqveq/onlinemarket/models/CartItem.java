package com.veqveq.onlinemarket.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cart_items_fld")
@NoArgsConstructor
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fld")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id_fld")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart_id_fld")
    private Cart cart;

    @Column(name = "count_fld")
    private int count;

    @Column(name = "cost_per_product_fld")
    private int costPerProduct;

    @Column(name = "created_at_fld")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at_fld")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public CartItem(Product product) {
        this.product = product;
        this.count = 1;
        this.costPerProduct = product.getCost();
    }

    public void decCount() {
        count--;
    }

    public void incCount() {
        count++;
    }
}
