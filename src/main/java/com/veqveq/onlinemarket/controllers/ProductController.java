package com.veqveq.onlinemarket.controllers;

import com.veqveq.onlinemarket.dto.ProductDto;
import com.veqveq.onlinemarket.exceptions.ResourceNotFoundException;
import com.veqveq.onlinemarket.services.ProductService;
import com.veqveq.onlinemarket.specifications.ProductSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    private ProductDto findById(@PathVariable long id) {
        return productService.findDtoById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Resource by id: %d not found", id)));
    }

    @GetMapping
    private Page<ProductDto> findByCost(@RequestParam MultiValueMap<String, String> params,
                                        @RequestParam(name = "numb", defaultValue = "1") Integer number,
                                        @RequestParam(name = "size", defaultValue = "10") Integer size
    ) {
        if (number < 1) {
            number = 1;
        }
        return productService.findAll(ProductSpecifications.build(params), number, size);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    private void save(@RequestBody ProductDto product) {
        productService.save(product);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    private void update(@RequestBody ProductDto product) {
        productService.update(product);
    }
}
