package com.nvminh162.domain.customer.repository;

import com.nvminh162.domain.customer.model.Customer;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CustomerRepository {
    private final Map<Long, Customer> customers = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public CustomerRepository() {
        // Initialize with sample data
        createSampleCustomers();
    }

    private void createSampleCustomers() {
        Customer customer1 = new Customer(
                idGenerator.getAndIncrement(),
                "John Doe",
                "john.doe@email.com",
                "+1234567890",
                "123 Main St, New York, NY 10001",
                LocalDateTime.now().minusMonths(6),
                LocalDateTime.now()
        );

        Customer customer2 = new Customer(
                idGenerator.getAndIncrement(),
                "Jane Smith",
                "jane.smith@email.com",
                "+1987654321",
                "456 Oak Ave, Los Angeles, CA 90001",
                LocalDateTime.now().minusMonths(3),
                LocalDateTime.now()
        );

        customers.put(customer1.getId(), customer1);
        customers.put(customer2.getId(), customer2);
    }

    public List<Customer> findAll() {
        return new ArrayList<>(customers.values());
    }

    public Optional<Customer> findById(Long id) {
        return Optional.ofNullable(customers.get(id));
    }

    public Optional<Customer> findByEmail(String email) {
        return customers.values().stream()
                .filter(customer -> customer.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public Customer save(Customer customer) {
        if (customer.getId() == null) {
            customer.setId(idGenerator.getAndIncrement());
            customer.setCreatedDate(LocalDateTime.now());
        }
        customer.setUpdatedDate(LocalDateTime.now());
        customers.put(customer.getId(), customer);
        return customer;
    }

    public void deleteById(Long id) {
        customers.remove(id);
    }

    public boolean existsById(Long id) {
        return customers.containsKey(id);
    }

    public boolean existsByEmail(String email) {
        return customers.values().stream()
                .anyMatch(customer -> customer.getEmail().equalsIgnoreCase(email));
    }
}
