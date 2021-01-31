package com.veqveq.onlinemarket.specifications;

import com.veqveq.onlinemarket.models.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;

public class ProductSpecifications {
    private static Specification<Product> priceGreaterThan(int minPrice) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> (criteriaBuilder.greaterThanOrEqualTo(root.get("cost"), minPrice));
    }

    private static Specification<Product> priceLessThan(int maxPrice) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> (criteriaBuilder.lessThanOrEqualTo(root.get("cost"), maxPrice));
    }

    private static Specification<Product> titleLike(String title) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> (criteriaBuilder.like(root.get("title"), String.format("%%%s%%", title)));
    }

    public static Specification<Product> build(MultiValueMap<String, String> filters) {
        Specification<Product> spec = Specification.where(null);
        if (filters.containsKey("min") && !filters.getFirst("min").isBlank()) {
            spec = spec.and(priceGreaterThan(Integer.parseInt(filters.getFirst("min"))));
        }
        if (filters.containsKey("max") && !filters.getFirst("max").isBlank()) {
            spec = spec.and(priceLessThan(Integer.parseInt(filters.getFirst("max"))));
        }
        if (filters.containsKey("title") && !filters.getFirst("title").isBlank()) {
            spec = spec.and(titleLike(filters.getFirst("title")));
        }
        return spec;
    }
}
