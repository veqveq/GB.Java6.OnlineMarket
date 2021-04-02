package com.veqveq.onlinemarket.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders_tbl")
@NoArgsConstructor
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fld")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id_fld")
    private User owner;

    @OneToMany(mappedBy = "order")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<OrderItem> orderItems;

    @Column(name = "total_price_fld")
    private int totalPrice;

    @Column(name = "address_fld")
    private String address;

    @Column(name = "created_at_fld")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at_fld")
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public Order(Cart cart, User user, String address) {
        this.owner = user;
        this.totalPrice = cart.getCartPrice();
        this.orderItems = new ArrayList<>();
        cart.getCartItems().stream().
                map(OrderItem::new)
                .forEach((oi) -> {
                    oi.setOrder(this);
                    orderItems.add(oi);
                });
        this.address = address;
    }
}
