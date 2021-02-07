package com.veqveq.onlinemarket.repositories;

import com.veqveq.onlinemarket.models.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findAllByOwnerUsername(String ownerUsername);
}
