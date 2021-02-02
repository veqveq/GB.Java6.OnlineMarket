package com.veqveq.onlinemarket.beans;

import com.veqveq.onlinemarket.exceptions.ResourceNotFoundException;
import com.veqveq.onlinemarket.models.Order;
import com.veqveq.onlinemarket.models.OrderItem;
import com.veqveq.onlinemarket.models.Product;
import com.veqveq.onlinemarket.repositories.OrderItemRepository;
import com.veqveq.onlinemarket.repositories.OrderRepository;
import com.veqveq.onlinemarket.services.ProductService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Data
public class Cart {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private List<OrderItem> orders;
    private int totalPrice;

    @PostConstruct
    private void init() {
        orders = new ArrayList<>();
    }

    public void addProduct(Long id) {
        for (OrderItem o : orders) {
            if (o.getProduct().getId().equals(id)) {
                o.incCount();
                return;
            }
        }
        Product addedProduct = productService.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Product by ID: %d not found", id)));
        orders.add(new OrderItem(addedProduct));
    }

    public void clean() {
        orders.clear();
    }

    public void decrementProduct(Long id) {
        for (OrderItem o : orders) {
            if (o.getProduct().getId().equals(id)) {
                if (o.getCount() == 1) {
                    orders.remove(o);
                    return;
                } else {
                    o.decCount();
                }
            }
        }
    }

    public void deleteProduct(Long id) {
        for (OrderItem o : orders) {
            if (o.getProduct().getId().equals(id)) {
                orders.remove(o);
                return;
            }
        }
        throw new ResourceNotFoundException(String.format("Product by ID: %d not found", id));
    }

    public void makeOrder(Order order) {
        orderRepository.save(order);
        for (OrderItem o : orders) {
            o.setOrder(order);
            orderItemRepository.save(o);
        }
    }
}

