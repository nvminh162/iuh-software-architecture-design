package com.nvminh162.bank_account.command.command;

import com.nvminh162.bank_account.command.aggregate.BankAccountAggregate;
import com.nvminh162.bank_account.command.event.AccountCreated;
import com.nvminh162.bank_account.command.event.AccountEvent;
import com.nvminh162.bank_account.command.event.MoneyDeposited;
import com.nvminh162.bank_account.command.event.MoneyWithdrawn;
import com.nvminh162.bank_account.command.model.CommandResult;
import com.nvminh162.bank_account.data.EventMapper;
import com.nvminh162.bank_account.data.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountCommandService {
    private final EventRepository eventRepository;

    public AccountCommandService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Transactional
    public CommandResult createAccount(String accountId, BigDecimal initialBalance) {
        validateAmount(initialBalance);
        ensureNotExists(accountId);

        append(new AccountCreated(accountId, initialBalance));
        return new CommandResult(accountId, initialBalance, "Account created");
    }

    @Transactional
    public CommandResult deposit(String accountId, BigDecimal amount) {
        validateAmount(amount);
        BankAccountAggregate account = loadAndReplay(accountId);

        append(new MoneyDeposited(accountId, amount));
        BigDecimal updatedBalance = account.getBalance().add(amount);
        return new CommandResult(accountId, updatedBalance, "Money deposited");
    }

    @Transactional
    public CommandResult withdraw(String accountId, BigDecimal amount) {
        validateAmount(amount);
        BankAccountAggregate account = loadAndReplay(accountId);
        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        append(new MoneyWithdrawn(accountId, amount));
        BigDecimal updatedBalance = account.getBalance().subtract(amount);
        return new CommandResult(accountId, updatedBalance, "Money withdrawn");
    }

    private BankAccountAggregate loadAndReplay(String accountId) {
        List<AccountEvent> history = eventRepository.findByAccountIdOrderByIdAsc(accountId)
                .stream()
                .map(EventMapper::toDomain)
                .toList();

        BankAccountAggregate account = new BankAccountAggregate();
        account.replay(history);
        if (!account.isCreated()) {
            throw new IllegalArgumentException("Account not found: " + accountId);
        }
        return account;
    }

    private void append(AccountEvent event) {
        eventRepository.save(EventMapper.toEntity(event));
    }

    private void ensureNotExists(String accountId) {
        boolean exists = !eventRepository.findByAccountIdOrderByIdAsc(accountId).isEmpty();
        if (exists) {
            throw new IllegalArgumentException("Account already exists: " + accountId);
        }
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be zero or positive");
        }
    }
}
