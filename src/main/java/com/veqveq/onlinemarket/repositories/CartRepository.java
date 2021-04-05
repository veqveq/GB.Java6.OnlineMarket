package com.veqveq.onlinemarket.repositories;

import com.veqveq.onlinemarket.models.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends CrudRepository<Cart, UUID> {
    Optional<Cart> findByUserId(Long userId);
}
