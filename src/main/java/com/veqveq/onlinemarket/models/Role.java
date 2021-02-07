package com.veqveq.onlinemarket.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "roles_tbl")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fld")
    private Long id;
    @Column(name = "role_fld")
    private String role;
    @CreationTimestamp
    @Column(name = "created_at_fld")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at_fld")
    private LocalDateTime updatedAt;
}
