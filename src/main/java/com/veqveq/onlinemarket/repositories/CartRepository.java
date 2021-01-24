package com.veqveq.onlinemarket.repositories;

import com.veqveq.onlinemarket.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Order, Long> {
    Optional<Order> findAllByProductId(Long id);
}
