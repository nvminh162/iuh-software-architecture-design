package com.nvminh162.service;

import com.nvminh162.exception.ProductNotFoundException;
import com.nvminh162.model.Product;
import com.nvminh162.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }

    public Product createProduct(Product product) {
        validateProduct(product);
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = getProductById(id);

        if (productDetails.getName() != null) {
            product.setName(productDetails.getName());
        }
        if (productDetails.getDescription() != null) {
            product.setDescription(productDetails.getDescription());
        }
        if (productDetails.getPrice() != null) {
            validatePrice(productDetails.getPrice());
            product.setPrice(productDetails.getPrice());
        }
        if (productDetails.getQuantity() != null) {
            validateQuantity(productDetails.getQuantity());
            product.setQuantity(productDetails.getQuantity());
        }

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    private void validateProduct(Product product) {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name is required");
        }
        validatePrice(product.getPrice());
        validateQuantity(product.getQuantity());
    }

    private void validatePrice(Double price) {
        if (price == null || price < 0) {
            throw new IllegalArgumentException("Product price must be greater than or equal to 0");
        }
    }

    private void validateQuantity(Integer quantity) {
        if (quantity == null || quantity < 0) {
            throw new IllegalArgumentException("Product quantity must be greater than or equal to 0");
        }
    }
}
