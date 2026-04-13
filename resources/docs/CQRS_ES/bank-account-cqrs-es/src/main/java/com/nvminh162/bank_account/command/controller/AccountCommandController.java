package com.nvminh162.bank_account.command.controller;

import com.nvminh162.bank_account.command.command.AccountCommandService;
import com.nvminh162.bank_account.command.model.AmountRequest;
import com.nvminh162.bank_account.command.model.CommandResult;
import com.nvminh162.bank_account.command.model.CreateAccountRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/commands/accounts")
public class AccountCommandController {
    private final AccountCommandService accountCommandService;

    public AccountCommandController(AccountCommandService accountCommandService) {
        this.accountCommandService = accountCommandService;
    }

    @PostMapping
    public CommandResult create(@RequestBody CreateAccountRequest request) {
        return accountCommandService.createAccount(request.accountId(), request.initialBalance());
    }

    @PostMapping("/{accountId}/deposit")
    public CommandResult deposit(@PathVariable String accountId, @RequestBody AmountRequest request) {
        return accountCommandService.deposit(accountId, request.amount());
    }

    @PostMapping("/{accountId}/withdraw")
    public CommandResult withdraw(@PathVariable String accountId, @RequestBody AmountRequest request) {
        return accountCommandService.withdraw(accountId, request.amount());
    }
}
