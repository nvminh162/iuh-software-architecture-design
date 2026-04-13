package com.nvminh162.bank_account.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
    List<EventEntity> findByAccountIdOrderByIdAsc(String accountId);
}
