package com.veqveq.onlinemarket.models;

import com.veqveq.onlinemarket.beans.Cart;
import com.veqveq.onlinemarket.dto.CartDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    @JoinColumn(name = "user_id_fld")
    private User user;
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
    @Column(name = "created_at_fld")
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(name = "updated_at_fld")
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public Order(User user) {
        this.user = user;
    }
}
