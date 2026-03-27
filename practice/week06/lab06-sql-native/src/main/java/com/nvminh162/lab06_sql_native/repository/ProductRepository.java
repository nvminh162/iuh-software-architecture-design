package com.nvminh162.lab06_sql_native.repository;

import com.nvminh162.lab06_sql_native.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM products WHERE name LIKE %:keyword%", nativeQuery = true)
    List<Product> searchByNameNative(String keyword);

    @Modifying
    @Query(value = "UPDATE products SET price = :price WHERE id = :id", nativeQuery = true)
    int updatePriceNative(Long id, Double price);
}
