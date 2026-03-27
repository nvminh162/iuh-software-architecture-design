package com.nvminh162.lab03.repository;

import com.nvminh162.lab03.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
