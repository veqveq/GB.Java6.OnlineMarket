package com.veqveq.onlinemarket.models;

import com.veqveq.onlinemarket.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "products_tbl")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fld")
    private Long id;
    @Column(name = "title_fld")
    private String title;
    @Column(name = "cost_fld")
    private int cost;
    @OneToMany(mappedBy = "product")
    List<Order> orders;
    @Column(name = "created_at_fld", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(name = "updated_at_fld")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Product(ProductDto productDto) {
        if (productDto.getId() != null) this.id = productDto.getId();
        this.title = productDto.getTitle();
        this.cost = productDto.getCost();
    }
}
