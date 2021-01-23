package com.veqveq.onlinemarket.services;

import com.veqveq.onlinemarket.dto.ProductDto;
import com.veqveq.onlinemarket.models.Product;
import com.veqveq.onlinemarket.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
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

    public void save(ProductDto product) {
        product.setId(null);
        productRepository.save(new Product(product));
    }

    public Optional<ProductDto> findById(Long id) {
        return productRepository.findById(id).map(ProductDto::new);
    }

    public Page<ProductDto> findAll(Specification<Product> spec, int page, int size) {
        return productRepository.findAll(spec, PageRequest.of(page - 1, size)).map(ProductDto::new);
    }

    public void update(ProductDto product) {
        if (findById(product.getId()).isPresent()) {
            Product currentProduct = productRepository.findById(product.getId()).get();
            if (product.getCost() != 0) {
                currentProduct.setCost(product.getCost());
            }
            if (product.getTitle() != null) {
                currentProduct.setTitle(product.getTitle());
            }
            productRepository.save(currentProduct);
        }
    }
}