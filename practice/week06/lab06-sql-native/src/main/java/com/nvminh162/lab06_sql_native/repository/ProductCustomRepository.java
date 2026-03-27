package com.nvminh162.lab06_sql_native.repository;

import com.nvminh162.lab06_sql_native.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<Product> findProductsByPriceGreaterThanNative(Double minPrice) {
        String sql = "SELECT * FROM products p WHERE p.price > :minPrice";
        return entityManager.createNativeQuery(sql, Product.class)
                .setParameter("minPrice", minPrice)
                .getResultList();
    }

    public void insertProductNative(String name, Double price) {
        String sql = "INSERT INTO products (name, price) VALUES (:name, :price)";
        entityManager.createNativeQuery(sql)
                .setParameter("name", name)
                .setParameter("price", price)
                .executeUpdate();
    }
}
