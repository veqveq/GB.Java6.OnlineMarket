package com.veqveq.onlinemarket.beans;

import com.veqveq.onlinemarket.exceptions.ResourceNotFoundException;
import com.veqveq.onlinemarket.models.Order;
import com.veqveq.onlinemarket.models.Product;
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
    private final ProductService productService;
    private List<Order> orders;
    private int totalPrice;

    @PostConstruct
    private void init() {
        orders = new ArrayList<>();
    }

    public void addProduct(Long id) {
        for (Order o : orders) {
            if (o.getProduct().getId().equals(id)) {
                o.incCount();
                return;
            }
        }
        Product addedProduct = productService.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Product by ID: %d not found", id)));
        orders.add(new Order(addedProduct));
    }

    public void clean() {
        orders.clear();
    }

    public void decrementProduct(Long id) {
        for (Order o : orders) {
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
        for (Order o : orders) {
            if (o.getProduct().getId().equals(id)) {
                orders.remove(o);
                return;
            }
        }
        throw new ResourceNotFoundException(String.format("Product by ID: %d not found", id));
    }

    public void calculateTotalPrice() {
        totalPrice = 0;
        for (Order o : orders) {
            totalPrice += o.getTotalCost();
        }
    }
}

