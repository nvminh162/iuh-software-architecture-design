package com.nvminh162.lab03.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    private String customerName;
    private List<OrderItemRequest> items;
}
