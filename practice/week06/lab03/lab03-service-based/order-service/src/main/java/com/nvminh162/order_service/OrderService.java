package com.nvminh162.order_service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    @Transactional
    public OrderEntity placeOrder(OrderRequest request) {
        OrderEntity order = new OrderEntity();
        order.setCustomerName(request.getCustomerName());
        order.setStatus("PENDING");
        order.setOrderTime(LocalDateTime.now());
        
        double total = 0.0;
        
        for (OrderItemRequest itemReq : request.getItems()) {
            // Make HTTP call to menu-service
            String url = "http://localhost:8082/api/menus/" + itemReq.getMenuItemId();
            MenuItemDto menuItem = restTemplate.getForObject(url, MenuItemDto.class);
            
            if (menuItem == null) {
                throw new RuntimeException("Menu item not found: " + itemReq.getMenuItemId());
            }
                    
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMenuItemId(menuItem.getId());
            orderItem.setQuantity(itemReq.getQuantity());
            orderItem.setPrice(menuItem.getPrice());
            
            order.getItems().add(orderItem);
            total += (menuItem.getPrice() * itemReq.getQuantity());
        }
        
        order.setTotalAmount(total);
        return orderRepository.save(order);
    }
}
