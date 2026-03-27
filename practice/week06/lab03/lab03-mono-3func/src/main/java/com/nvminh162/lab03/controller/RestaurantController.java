package com.nvminh162.lab03.controller;

import com.nvminh162.lab03.entity.MenuItem;
import com.nvminh162.lab03.entity.Restaurant;
import com.nvminh162.lab03.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @GetMapping("/{id}/menu")
    public ResponseEntity<List<MenuItem>> getMenuByRestaurantId(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.getMenuByRestaurantId(id));
    }
}
