package com.nvminh162.bank_account.query.controller;

import com.nvminh162.bank_account.query.model.AccountBalanceView;
import com.nvminh162.bank_account.query.projection.AccountProjectionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/queries/accounts")
public class AccountQueryController {
    private final AccountProjectionService accountProjectionService;

    public AccountQueryController(AccountProjectionService accountProjectionService) {
        this.accountProjectionService = accountProjectionService;
    }

    @GetMapping("/{accountId}/balance")
    public AccountBalanceView getBalance(@PathVariable String accountId) {
        return accountProjectionService.getBalance(accountId);
    }
}
