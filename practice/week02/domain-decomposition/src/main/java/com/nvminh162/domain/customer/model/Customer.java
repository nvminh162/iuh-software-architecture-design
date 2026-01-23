package com.nvminh162.domain.customer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
