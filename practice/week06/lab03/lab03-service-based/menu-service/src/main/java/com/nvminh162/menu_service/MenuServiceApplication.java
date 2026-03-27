package com.nvminh162.menu_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MenuServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MenuServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(MenuItemRepository menuItemRepository) {
		return args -> {
			if (menuItemRepository.count() == 0) {
				menuItemRepository.save(new MenuItem(null, "Pepperoni Pizza", 12.99, 1L));
				menuItemRepository.save(new MenuItem(null, "Cheese Pizza", 10.99, 1L));
				
				menuItemRepository.save(new MenuItem(null, "Whopper", 6.99, 2L));
				menuItemRepository.save(new MenuItem(null, "Fries", 2.99, 2L));
			}
		};
	}
}
