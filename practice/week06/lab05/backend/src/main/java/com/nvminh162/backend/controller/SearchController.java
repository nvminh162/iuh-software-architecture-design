package com.nvminh162.backend.controller;

import com.nvminh162.backend.model.Product;
import com.nvminh162.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class SearchController {

    private final ProductRepository productRepository;

    @GetMapping("/search")
    public List<Product> search(@RequestParam(value = "q", defaultValue = "") String keyword) {
        return productRepository.searchProducts(keyword);
    }
}
