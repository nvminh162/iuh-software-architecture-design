package com.vminh.food_backend.food;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/foods")
public class FoodItemController {

    private final FoodItemRepository foodItemRepository;

    public FoodItemController(FoodItemRepository foodItemRepository) {
        this.foodItemRepository = foodItemRepository;
    }

    @GetMapping
    public List<FoodItem> findAll() {
        return foodItemRepository.findAll();
    }
}
