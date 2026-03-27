package com.nvminh162.order_service;

import lombok.Data;

@Data
public class MenuItemDto {
    private Long id;
    private String name;
    private Double price;
    private Long restaurantId;
}
