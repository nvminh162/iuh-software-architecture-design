package com.nvminh162.order_service;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    private String customerName;
    private List<OrderItemRequest> items;
}
