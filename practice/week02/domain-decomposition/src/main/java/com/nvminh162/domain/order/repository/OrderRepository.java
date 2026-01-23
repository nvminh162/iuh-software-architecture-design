package com.nvminh162.domain.order.repository;

import com.nvminh162.domain.order.model.Order;
import com.nvminh162.domain.order.model.OrderItem;
import com.nvminh162.domain.order.model.OrderStatus;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class OrderRepository {
    private final Map<Long, Order> orders = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public OrderRepository() {
        // Initialize with sample data
        createSampleOrders();
    }

    private void createSampleOrders() {
        Order order1 = new Order(
                idGenerator.getAndIncrement(),
                1L,
                Arrays.asList(
                        new OrderItem(1L, "Laptop", 1, 1200.00),
                        new OrderItem(2L, "Mouse", 2, 25.00)
                ),
                1250.00,
                OrderStatus.DELIVERED,
                LocalDateTime.now().minusDays(5),
                LocalDateTime.now().minusDays(2)
        );

        Order order2 = new Order(
                idGenerator.getAndIncrement(),
                2L,
                Arrays.asList(
                        new OrderItem(3L, "Keyboard", 1, 80.00)
                ),
                80.00,
                OrderStatus.PROCESSING,
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now()
        );

        orders.put(order1.getId(), order1);
        orders.put(order2.getId(), order2);
    }

    public List<Order> findAll() {
        return new ArrayList<>(orders.values());
    }

    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(orders.get(id));
    }

    public List<Order> findByCustomerId(Long customerId) {
        return orders.values().stream()
                .filter(order -> order.getCustomerId().equals(customerId))
                .toList();
    }

    public Order save(Order order) {
        if (order.getId() == null) {
            order.setId(idGenerator.getAndIncrement());
            order.setOrderDate(LocalDateTime.now());
        }
        order.setUpdatedDate(LocalDateTime.now());
        orders.put(order.getId(), order);
        return order;
    }

    public void deleteById(Long id) {
        orders.remove(id);
    }

    public boolean existsById(Long id) {
        return orders.containsKey(id);
    }
}
