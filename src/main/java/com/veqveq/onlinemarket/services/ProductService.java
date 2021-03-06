package com.veqveq.onlinemarket.services;

import com.veqveq.onlinemarket.dto.ProductDto;
import com.veqveq.onlinemarket.models.Product;
import com.veqveq.onlinemarket.repositories.ProductRepository;
import com.veqveq.onlinemarket.ws.soap.products.ProductSoap;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
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

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Optional<ProductDto> findDtoById(Long id) {
        return productRepository.findById(id).map(ProductDto::new);
    }

    public Page<ProductDto> findAll(Specification<Product> spec, int page, int size) {
        return productRepository.findAll(spec, PageRequest.of(page - 1, size)).map(ProductDto::new);
    }

    public void update(ProductDto product) {
        if (findDtoById(product.getId()).isPresent()) {
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

    public List<ProductSoap> findAll() {
        return productRepository.findAll().stream().map(functionEntityToSoap).collect(Collectors.toList());
    }

    public static final Function<Product, ProductSoap> functionEntityToSoap = p -> {
        ProductSoap ps = new ProductSoap();
        ps.setId(p.getId());
        ps.setTitle(p.getTitle());
        ps.setCost(p.getCost());
        ps.setCreatedAt(convertDate(p.getCreatedAt()));
        ps.setUpdatedAt(convertDate(p.getUpdatedAt()));
        return ps;
    };

    private static XMLGregorianCalendar convertDate(LocalDateTime dateTime) {
        try {
            GregorianCalendar gcal = GregorianCalendar.from(dateTime.toLocalDate().atStartOfDay(ZoneId.systemDefault()));
            XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
            return xcal;
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
            return null;
        }
    }
}