package com.veqveq.onlinemarket.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cart_items_tbl")
<<<<<<< HEAD
@NoArgsConstructor
@Data
=======
@Data
@NoArgsConstructor
>>>>>>> update_cart
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fld")
    private Long id;

    @ManyToOne
<<<<<<< HEAD
    @JoinColumn(name = "product_id_fld")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart_id_fld")
    private Cart cart;

=======
    @JoinColumn(name = "cart_id_fld")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id_fld")
    private Product product;

>>>>>>> update_cart
    @Column(name = "count_fld")
    private int count;

    @Column(name = "cost_per_product_fld")
<<<<<<< HEAD
    private int costPerProduct;

    @Column(name = "created_at_fld")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at_fld")
    @UpdateTimestamp
=======
    private int cost;

    @Column(name = "item_price_fld")
    private int itemPrice;

    @CreationTimestamp
    @Column(name = "created_at_fld")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at_fld")
>>>>>>> update_cart
    private LocalDateTime updatedAt;

    public CartItem(Product product) {
        this.product = product;
<<<<<<< HEAD
        this.count = 1;
        this.costPerProduct = product.getCost();
    }

    public void decCount() {
        count--;
    }

    public void incCount() {
        count++;
=======
        this.cost = product.getCost();
        this.count = 1;
        recalculate();
    }

    public void inc() {
        count++;
        recalculate();
    }

    public void dec() {
        count--;
        recalculate();
    }

    private void recalculate() {
        itemPrice = count * cost;
>>>>>>> update_cart
    }
}
