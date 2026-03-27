package com.nvminh162.lab02.vertical.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nvminh162.lab02.vertical.entity.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
}
