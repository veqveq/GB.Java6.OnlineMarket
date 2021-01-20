package com.veqveq.onlinemarket.repositories;

import com.veqveq.onlinemarket.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByCostIsBetween(int minPrice, int maxPrice, Pageable page);
}
