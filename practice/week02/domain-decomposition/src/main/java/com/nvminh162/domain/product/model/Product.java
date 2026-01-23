package com.nvminh162.domain.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private String category;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
