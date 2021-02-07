package com.veqveq.onlinemarket.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users_tbl")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fld")
    private Long id;
    @Column(name = "username_fld")
    private String username;
    @Column(name = "password_fld")
    private String password;
    @ManyToMany
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinTable(name = "users_roles_tbl",
            joinColumns = @JoinColumn(name = "user_id_fld"),
            inverseJoinColumns = @JoinColumn(name = "role_id_fld"))
    private List<Role> roles;
    @OneToMany(mappedBy = "owner")
    private List<Order> orders;
    @CreationTimestamp
    @Column(name = "created_at_fld")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at_fld")
    private LocalDateTime updatedAt;

    public User(String username, String password, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
