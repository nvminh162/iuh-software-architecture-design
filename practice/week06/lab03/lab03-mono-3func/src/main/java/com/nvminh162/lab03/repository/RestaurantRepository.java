package com.nvminh162.lab03.repository;

import com.nvminh162.lab03.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
