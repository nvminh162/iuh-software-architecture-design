package com.nvminh162.lab03.service;

import com.nvminh162.lab03.entity.MenuItem;
import com.nvminh162.lab03.entity.Restaurant;
import com.nvminh162.lab03.repository.MenuItemRepository;
import com.nvminh162.lab03.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public List<MenuItem> getMenuByRestaurantId(Long restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId);
    }
}
