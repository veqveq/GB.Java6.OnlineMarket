package com.veqveq.onlinemarket.repositories;

import com.veqveq.onlinemarket.models.OrderItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem,Long> {
}
