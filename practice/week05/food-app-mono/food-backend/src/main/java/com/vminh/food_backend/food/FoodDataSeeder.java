package com.vminh.food_backend.food;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FoodDataSeeder implements CommandLineRunner {

    private final FoodItemRepository foodItemRepository;

    public FoodDataSeeder(FoodItemRepository foodItemRepository) {
        this.foodItemRepository = foodItemRepository;
    }

    @Override
    public void run(String... args) {
        if (foodItemRepository.count() > 0) {
            return;
        }

        List<FoodItem> items = List.of(
            new FoodItem("Truffle Beef Burger", new BigDecimal("12.90"), "Burgers", "Best Seller", "15-20 min", 4.9),
            new FoodItem("Salmon Poke Bowl", new BigDecimal("14.50"), "Healthy", "Fresh Pick", "18-24 min", 4.8),
            new FoodItem("Roasted Chicken Pasta", new BigDecimal("11.90"), "Pasta", "Top Rated", "15-22 min", 4.8),
            new FoodItem("Margherita Artisan", new BigDecimal("10.70"), "Pizza", "Chef Choice", "12-18 min", 4.7),
            new FoodItem("Vegan Green Delight", new BigDecimal("9.90"), "Healthy", "Healthy", "10-16 min", 4.9),
            new FoodItem("Classic Tonkotsu Ramen", new BigDecimal("13.40"), "Ramen", "Trending", "20-25 min", 4.7)
        );

        foodItemRepository.saveAll(items);
    }
}
