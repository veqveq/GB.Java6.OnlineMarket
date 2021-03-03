package com.veqveq.onlinemarket.repositories;

import com.veqveq.onlinemarket.models.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepository extends CrudRepository<Role,Long> {
    List<Role> findByRole(String role);
}
