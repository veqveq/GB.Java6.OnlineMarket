package com.veqveq.onlinemarket.services;

import com.veqveq.onlinemarket.dto.ProductDto;
import com.veqveq.onlinemarket.models.Product;
import com.veqveq.onlinemarket.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public void saveOrUpdate(Product product) {
        productRepository.save(product);
    }

    public Optional<ProductDto> findById(Long id) {
        return productRepository.findById(id).stream().map(ProductDto::new).findFirst();
    }

    public Page<ProductDto> findAllByCost(int minPrice, int maxPrice, int page, int size) {
        Page<Product> originalPage = productRepository.findAllByCostIsBetween(minPrice, maxPrice, PageRequest.of(page - 1, size));
        return new PageImpl<>(originalPage.getContent().stream().map(ProductDto::new).collect(Collectors.toList()),
                originalPage.getPageable(),
                originalPage.getTotalElements());
    }
}