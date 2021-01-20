package com.veqveq.onlinemarket.controllers;

import com.veqveq.onlinemarket.dto.ProductDto;
import com.veqveq.onlinemarket.models.Product;
import com.veqveq.onlinemarket.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    private Optional<ProductDto> findById(@PathVariable long id) {
        return productService.findById(id);
    }

    @GetMapping
    private Page<ProductDto> findByCost(@RequestParam(name = "numb", defaultValue = "1") Integer number,
                                        @RequestParam(name = "size", defaultValue = "10") Integer size,
                                        @RequestParam(name = "min", defaultValue = "0") Integer minPrice,
                                        @RequestParam(name = "max", required = false) Integer maxPrice
    ) {
        if (number < 1) {
            number = 1;
        }
        if (maxPrice == null) {
            maxPrice = Integer.MAX_VALUE;
        }
        return productService.findAllByCost(minPrice, maxPrice, number, size);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PostMapping
    private void save(@RequestBody ProductDto product) {
        product.setId(null);
        productService.saveOrUpdate(new Product(product));
    }

    @PutMapping
    private void update(@RequestBody ProductDto product) {
        productService.saveOrUpdate(new Product(product));
    }
}
