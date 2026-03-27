package com.nvminh162.restaurant_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestaurantServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(RestaurantRepository restaurantRepository) {
		return args -> {
			if (restaurantRepository.count() == 0) {
				restaurantRepository.save(new Restaurant(null, "Pizza Hut", "123 Main St"));
				restaurantRepository.save(new Restaurant(null, "Burger King", "456 Elm St"));
			}
		};
	}
}
