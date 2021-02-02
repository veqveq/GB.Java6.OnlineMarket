package com.veqveq.onlinemarket.repositories;

import com.veqveq.onlinemarket.models.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order,Long> {
}
