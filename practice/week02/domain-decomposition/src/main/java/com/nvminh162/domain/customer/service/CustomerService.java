package com.nvminh162.domain.customer.service;

import com.nvminh162.domain.customer.model.Customer;
import com.nvminh162.domain.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Customer createCustomer(Customer customer) {
        // Check if email already exists
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + customer.getEmail());
        }
        return customerRepository.save(customer);
    }

    public Optional<Customer> updateCustomer(Long id, Customer customerDetails) {
        return customerRepository.findById(id)
                .map(existingCustomer -> {
                    if (customerDetails.getName() != null) {
                        existingCustomer.setName(customerDetails.getName());
                    }
                    if (customerDetails.getEmail() != null && 
                        !customerDetails.getEmail().equals(existingCustomer.getEmail())) {
                        if (customerRepository.existsByEmail(customerDetails.getEmail())) {
                            throw new IllegalArgumentException("Email already exists: " + customerDetails.getEmail());
                        }
                        existingCustomer.setEmail(customerDetails.getEmail());
                    }
                    if (customerDetails.getPhone() != null) {
                        existingCustomer.setPhone(customerDetails.getPhone());
                    }
                    if (customerDetails.getAddress() != null) {
                        existingCustomer.setAddress(customerDetails.getAddress());
                    }
                    return customerRepository.save(existingCustomer);
                });
    }

    public boolean deleteCustomer(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
