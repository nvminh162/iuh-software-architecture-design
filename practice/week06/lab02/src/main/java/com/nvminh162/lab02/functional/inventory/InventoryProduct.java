package com.nvminh162.lab02.functional.inventory;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory_product")
public class InventoryProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String productCode;
    private int quantityStock;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }
    
    public int getQuantityStock() { return quantityStock; }
    public void setQuantityStock(int quantityStock) { this.quantityStock = quantityStock; }
}
