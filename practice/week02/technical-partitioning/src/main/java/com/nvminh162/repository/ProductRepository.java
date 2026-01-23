package com.nvminh162.repository;

import com.nvminh162.model.Product;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProductRepository {
    private final Map<Long, Product> database = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public ProductRepository() {
        // Initialize with sample data
        save(new Product(null, "Laptop", "Gaming laptop", 1500.0, 10, null, null));
        save(new Product(null, "Mouse", "Wireless mouse", 25.0, 50, null, null));
        save(new Product(null, "Keyboard", "Mechanical keyboard", 100.0, 30, null, null));
    }

    public Product save(Product product) {
        if (product.getId() == null) {
            product.setId(idGenerator.getAndIncrement());
            product.setCreatedAt(LocalDateTime.now());
        }
        product.setUpdatedAt(LocalDateTime.now());
        database.put(product.getId(), product);
        return product;
    }

    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(database.get(id));
    }

    public List<Product> findAll() {
        return new ArrayList<>(database.values());
    }

    public void deleteById(Long id) {
        database.remove(id);
    }

    public boolean existsById(Long id) {
        return database.containsKey(id);
    }
}
