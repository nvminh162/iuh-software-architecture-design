package com.nvminh162.domain.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    private Long customerId;
    private List<OrderItem> items;
    private Double totalAmount;
    private OrderStatus status;
    private LocalDateTime orderDate;
    private LocalDateTime updatedDate;
}
