package com.nvminh162.lab03;

import com.nvminh162.lab03.entity.MenuItem;
import com.nvminh162.lab03.entity.Restaurant;
import com.nvminh162.lab03.repository.MenuItemRepository;
import com.nvminh162.lab03.repository.RestaurantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Lab03Application {

	public static void main(String[] args) {
		SpringApplication.run(Lab03Application.class, args);
	}

	@Bean
	public CommandLineRunner initData(RestaurantRepository restaurantRepository, MenuItemRepository menuItemRepository) {
		return args -> {
			if (restaurantRepository.count() == 0) {
				Restaurant r1 = new Restaurant(null, "Pizza Hut", "123 Main St");
				Restaurant r2 = new Restaurant(null, "Burger King", "456 Elm St");
				
				restaurantRepository.save(r1);
				restaurantRepository.save(r2);
				
				menuItemRepository.save(new MenuItem(null, "Pepperoni Pizza", 12.99, r1));
				menuItemRepository.save(new MenuItem(null, "Cheese Pizza", 10.99, r1));
				
				menuItemRepository.save(new MenuItem(null, "Whopper", 6.99, r2));
				menuItemRepository.save(new MenuItem(null, "Fries", 2.99, r2));
			}
		};
	}
}
