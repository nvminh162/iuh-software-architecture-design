package com.nvminh162.lab03.service;

import com.nvminh162.lab03.dto.OrderItemRequest;
import com.nvminh162.lab03.dto.OrderRequest;
import com.nvminh162.lab03.entity.MenuItem;
import com.nvminh162.lab03.entity.OrderEntity;
import com.nvminh162.lab03.entity.OrderItem;
import com.nvminh162.lab03.repository.MenuItemRepository;
import com.nvminh162.lab03.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;

    @Transactional
    public OrderEntity placeOrder(OrderRequest request) {
        OrderEntity order = new OrderEntity();
        order.setCustomerName(request.getCustomerName());
        order.setStatus("PENDING");
        order.setOrderTime(LocalDateTime.now());
        
        double total = 0.0;
        
        for (OrderItemRequest itemReq : request.getItems()) {
            MenuItem menuItem = menuItemRepository.findById(itemReq.getMenuItemId())
                    .orElseThrow(() -> new RuntimeException("Menu item not found: " + itemReq.getMenuItemId()));
                    
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(itemReq.getQuantity());
            orderItem.setPrice(menuItem.getPrice());
            
            order.getItems().add(orderItem);
            total += (menuItem.getPrice() * itemReq.getQuantity());
        }
        
        order.setTotalAmount(total);
        return orderRepository.save(order);
    }
}
