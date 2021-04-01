package com.veqveq.onlinemarket.repositories;

import com.veqveq.onlinemarket.models.Cart;
import org.springframework.data.repository.CrudRepository;
<<<<<<< HEAD

import java.util.UUID;

=======
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
>>>>>>> update_cart
public interface CartRepository extends CrudRepository<Cart, UUID> {
}
