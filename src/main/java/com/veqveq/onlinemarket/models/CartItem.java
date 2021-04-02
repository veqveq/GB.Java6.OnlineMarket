package com.veqveq.onlinemarket.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cart_items_tbl")
@Data
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fld")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id_fld")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id_fld")
    private Product product;

    @Column(name = "count_fld")
    private int count;

    @Column(name = "cost_per_product_fld")
    private int cost;

    @Column(name = "item_price_fld")
    private int itemPrice;

    @CreationTimestamp
    @Column(name = "created_at_fld")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at_fld")
    private LocalDateTime updatedAt;

    public CartItem(Product product) {
        this.product = product;
        this.cost = product.getCost();
        this.count = 1;
        this.itemPrice = count * cost;
    }

    public void inc() {
        count++;
        recalculate();
    }

    public void dec() {
        count--;
        recalculate();
    }

    public void recalculate() {
        itemPrice = count * cost;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", cart=" + cart.getId() +
                ", product=" + product +
                ", count=" + count +
                ", cost=" + cost +
                ", itemPrice=" + itemPrice +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
