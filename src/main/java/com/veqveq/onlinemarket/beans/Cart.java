package com.veqveq.onlinemarket.beans;

import com.veqveq.onlinemarket.dto.OrderItemDto;
import com.veqveq.onlinemarket.exceptions.ResourceNotFoundException;
import com.veqveq.onlinemarket.models.OrderItem;
import com.veqveq.onlinemarket.models.Product;
import com.veqveq.onlinemarket.services.ProductService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Data
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cart {
    private final ProductService productService;
    private List<OrderItem> orders;
    private int totalPrice;

    @PostConstruct
    private void init() {
        orders = new ArrayList<>();
    }

    public void addProduct(Long id) {
        try {
            for (OrderItem o : orders) {
                if (o.getProduct().getId().equals(id)) {
                    o.incCount();
                    return;
                }
            }
            Product addedProduct = productService.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException(String.format("Product by ID: %d not found", id)));
            orders.add(new OrderItem(addedProduct));
        }finally {
            calculateTotalPrice();
        }
    }

    public void clean() {
        orders.clear();
        calculateTotalPrice();
    }

    public void decrementProduct(Long id) {
        try {
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
        }finally {
            calculateTotalPrice();
        }
    }

    public void deleteProduct(Long id) {
        for (OrderItem o : orders) {
            if (o.getProduct().getId().equals(id)) {
                orders.remove(o);
                calculateTotalPrice();
                return;
            }
        }
        throw new ResourceNotFoundException(String.format("Product by ID: %d not found", id));
    }

    private void calculateTotalPrice() {
        totalPrice = 0;
        for (OrderItem o : orders) {
            totalPrice += o.getCostPerProduct() * o.getCount();
        }
    }
}

