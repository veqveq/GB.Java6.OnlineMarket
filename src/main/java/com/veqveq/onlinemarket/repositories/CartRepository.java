package com.veqveq.onlinemarket.repositories;

import com.veqveq.onlinemarket.models.Cart;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CartRepository extends CrudRepository<Cart, UUID> {
}
