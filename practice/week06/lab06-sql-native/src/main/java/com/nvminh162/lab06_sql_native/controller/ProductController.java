package com.nvminh162.lab06_sql_native.controller;

import com.nvminh162.lab06_sql_native.entity.Product;
import com.nvminh162.lab06_sql_native.repository.ProductCustomRepository;
import com.nvminh162.lab06_sql_native.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCustomRepository productCustomRepository;

    // 1. Create product using standard repository
    @PostMapping("/jpa")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productRepository.save(product));
    }

    // 2. Create product using EntityManager Native Query
    @PostMapping("/native-jdbc")
    @Transactional
    public ResponseEntity<String> createProductNative(@RequestBody Product product) {
        productCustomRepository.insertProductNative(product.getName(), product.getPrice());
        return ResponseEntity.ok("Product inserted successfully using EntityManager native query");
    }

    // 3. Get all products normally
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    // 4. Search products by name using @Query native query
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProductsNative(@RequestParam String keyword) {
        return ResponseEntity.ok(productRepository.searchByNameNative(keyword));
    }

    // 5. Filter products by min price using EntityManager Native query
    @GetMapping("/filter")
    public ResponseEntity<List<Product>> filterProductsNative(@RequestParam Double minPrice) {
        return ResponseEntity.ok(productCustomRepository.findProductsByPriceGreaterThanNative(minPrice));
    }

    // 6. Update price using @Query native query
    @PutMapping("/{id}/price")
    @Transactional
    public ResponseEntity<String> updateProductPriceNative(@PathVariable Long id, @RequestParam Double price) {
        int updatedCount = productRepository.updatePriceNative(id, price);
        if (updatedCount > 0) {
            return ResponseEntity.ok("Product price updated via native query");
        }
        return ResponseEntity.notFound().build();
    }
}
