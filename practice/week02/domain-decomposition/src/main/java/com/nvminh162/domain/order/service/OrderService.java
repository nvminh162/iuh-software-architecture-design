package com.nvminh162.domain.order.service;

import com.nvminh162.domain.order.model.Order;
import com.nvminh162.domain.order.model.OrderStatus;
import com.nvminh162.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public Order createOrder(Order order) {
        // Calculate total amount
        double total = order.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        order.setTotalAmount(total);
        order.setStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    public Optional<Order> updateOrder(Long id, Order orderDetails) {
        return orderRepository.findById(id)
                .map(existingOrder -> {
                    if (orderDetails.getItems() != null) {
                        existingOrder.setItems(orderDetails.getItems());
                        double total = orderDetails.getItems().stream()
                                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                                .sum();
                        existingOrder.setTotalAmount(total);
                    }
                    if (orderDetails.getStatus() != null) {
                        existingOrder.setStatus(orderDetails.getStatus());
                    }
                    return orderRepository.save(existingOrder);
                });
    }

    public Optional<Order> updateOrderStatus(Long id, OrderStatus status) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setStatus(status);
                    return orderRepository.save(order);
                });
    }

    public boolean deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
