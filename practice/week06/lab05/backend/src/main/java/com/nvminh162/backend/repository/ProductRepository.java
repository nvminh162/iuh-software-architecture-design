package com.nvminh162.backend.repository;

import com.nvminh162.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "CALL search_products(:searchTerm)", nativeQuery = true)
    List<Product> searchProducts(@Param("searchTerm") String searchTerm);
}
