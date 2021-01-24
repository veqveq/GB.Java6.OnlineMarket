package com.veqveq.onlinemarket.controllers;

import com.veqveq.onlinemarket.dto.OrderDto;
import com.veqveq.onlinemarket.models.Product;
import com.veqveq.onlinemarket.services.CartService;
import com.veqveq.onlinemarket.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final ProductService productService;
    private final CartService cartService;

    @GetMapping
    private List<OrderDto> findAll() {
        return cartService.findAll();
    }

    @GetMapping("/delete/{id}")
    private void delete(@PathVariable Long id) {
        if (cartService.findByOrderId(id).get().getCount() > 1) {
            cartService.update(cartService.findByOrderId(id).get(), -1);
        } else {
            cartService.deleteById(id);
        }
    }

    @GetMapping("/add/{id}")
    private void save(@PathVariable Long id) {
        if (productService.findById(id).isPresent()) {
            if (cartService.findByProductId(id).isPresent()) {
                cartService.update(cartService.findByProductId(id).get(), 1);
            } else {
                cartService.save(new Product(productService.findById(id).get()));
            }
        }
    }
}
