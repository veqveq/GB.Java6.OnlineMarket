package com.veqveq.onlinemarket.repositories;

import com.veqveq.onlinemarket.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    void removeAllByCart_Id(UUID cartId);

    void removeByProduct_Id(Long productId);
}
