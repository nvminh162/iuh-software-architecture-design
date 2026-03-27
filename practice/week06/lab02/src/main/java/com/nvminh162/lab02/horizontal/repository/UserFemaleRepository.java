package com.nvminh162.lab02.horizontal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nvminh162.lab02.horizontal.entity.UserFemale;

public interface UserFemaleRepository extends JpaRepository<UserFemale, Long> {
}
