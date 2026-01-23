package com.nvminh162.domain.product.service;

import com.nvminh162.domain.product.model.Product;
import com.nvminh162.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> updateProduct(Long id, Product productDetails) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    if (productDetails.getName() != null) {
                        existingProduct.setName(productDetails.getName());
                    }
                    if (productDetails.getDescription() != null) {
                        existingProduct.setDescription(productDetails.getDescription());
                    }
                    if (productDetails.getPrice() != null) {
                        existingProduct.setPrice(productDetails.getPrice());
                    }
                    if (productDetails.getStock() != null) {
                        existingProduct.setStock(productDetails.getStock());
                    }
                    if (productDetails.getCategory() != null) {
                        existingProduct.setCategory(productDetails.getCategory());
                    }
                    return productRepository.save(existingProduct);
                });
    }

    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Product> updateStock(Long id, Integer quantity) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setStock(product.getStock() + quantity);
                    return productRepository.save(product);
                });
    }
}
