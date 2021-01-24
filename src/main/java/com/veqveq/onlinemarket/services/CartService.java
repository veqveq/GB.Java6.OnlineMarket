package com.veqveq.onlinemarket.services;

import com.veqveq.onlinemarket.dto.OrderDto;
import com.veqveq.onlinemarket.models.Order;
import com.veqveq.onlinemarket.models.Product;
import com.veqveq.onlinemarket.repositories.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    public void deleteById(Long id) {
        cartRepository.deleteById(id);
    }

    public void save(Product product) {
        cartRepository.save(new Order(product));
    }

    public Optional<Order> findByOrderId(Long id){
        return cartRepository.findById(id);
    }

    public Optional<Order> findByProductId(Long id) {
        return cartRepository.findAllByProductId(id);
    }

    public List<OrderDto> findAll() {
        return cartRepository.findAll().stream().map(OrderDto::new).collect(Collectors.toList());
    }

    public void update(Order order, int dCount) {
        order.setCount(order.getCount() + dCount);
        cartRepository.save(order);
    }

    public void clean(){
        cartRepository.deleteAll();
    }

}
