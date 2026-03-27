package com.nvminh162.lab02.horizontal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nvminh162.lab02.horizontal.entity.UserMale;

public interface UserMaleRepository extends JpaRepository<UserMale, Long> {
}
