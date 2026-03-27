package com.nvminh162.lab02.functional.inventory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryProductRepository extends JpaRepository<InventoryProduct, Long> {
}
