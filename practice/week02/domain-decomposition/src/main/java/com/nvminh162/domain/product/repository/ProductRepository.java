package com.nvminh162.domain.product.repository;

import com.nvminh162.domain.product.model.Product;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProductRepository {
    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public ProductRepository() {
        // Initialize with sample data
        createSampleProducts();
    }

    private void createSampleProducts() {
        Product product1 = new Product(
                idGenerator.getAndIncrement(),
                "Laptop",
                "High-performance laptop for professionals",
                1200.00,
                50,
                "Electronics",
                LocalDateTime.now().minusDays(30),
                LocalDateTime.now()
        );

        Product product2 = new Product(
                idGenerator.getAndIncrement(),
                "Mouse",
                "Wireless optical mouse",
                25.00,
                200,
                "Electronics",
                LocalDateTime.now().minusDays(20),
                LocalDateTime.now()
        );

        Product product3 = new Product(
                idGenerator.getAndIncrement(),
                "Keyboard",
                "Mechanical gaming keyboard",
                80.00,
                100,
                "Electronics",
                LocalDateTime.now().minusDays(15),
                LocalDateTime.now()
        );

        products.put(product1.getId(), product1);
        products.put(product2.getId(), product2);
        products.put(product3.getId(), product3);
    }

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(products.get(id));
    }

    public List<Product> findByCategory(String category) {
        return products.values().stream()
                .filter(product -> product.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    public Product save(Product product) {
        if (product.getId() == null) {
            product.setId(idGenerator.getAndIncrement());
            product.setCreatedDate(LocalDateTime.now());
        }
        product.setUpdatedDate(LocalDateTime.now());
        products.put(product.getId(), product);
        return product;
    }

    public void deleteById(Long id) {
        products.remove(id);
    }

    public boolean existsById(Long id) {
        return products.containsKey(id);
    }
}
