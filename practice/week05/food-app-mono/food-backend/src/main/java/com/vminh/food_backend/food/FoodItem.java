package com.vminh.food_backend.food;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "food_items")
public class FoodItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false, length = 60)
    private String category;

    @Column(nullable = false, length = 40)
    private String tag;

    @Column(nullable = false, length = 30)
    private String estimatedTime;

    @Column(nullable = false)
    private double rating;

    protected FoodItem() {
    }

    public FoodItem(String name, BigDecimal price, String category, String tag, String estimatedTime, double rating) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.tag = tag;
        this.estimatedTime = estimatedTime;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getTag() {
        return tag;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public double getRating() {
        return rating;
    }
}
