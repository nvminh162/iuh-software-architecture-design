package com.nvminh162.bank_account.command.aggregate;

import com.nvminh162.bank_account.command.event.AccountCreated;
import com.nvminh162.bank_account.command.event.AccountEvent;
import com.nvminh162.bank_account.command.event.MoneyDeposited;
import com.nvminh162.bank_account.command.event.MoneyWithdrawn;

import java.math.BigDecimal;
import java.util.List;

public class BankAccountAggregate {
    private String accountId;
    private BigDecimal balance = BigDecimal.ZERO;
    private boolean created;

    public void apply(AccountEvent event) {
        if (event instanceof AccountCreated accountCreated) {
            this.accountId = accountCreated.accountId();
            this.balance = accountCreated.amount();
            this.created = true;
            return;
        }

        if (!created) {
            throw new IllegalStateException("Account must be created before monetary operations");
        }

        if (event instanceof MoneyDeposited deposited) {
            this.balance = this.balance.add(deposited.amount());
            return;
        }

        if (event instanceof MoneyWithdrawn withdrawn) {
            this.balance = this.balance.subtract(withdrawn.amount());
            return;
        }

        throw new IllegalArgumentException("Unsupported event type: " + event.getClass().getSimpleName());
    }

    public void replay(List<AccountEvent> events) {
        for (AccountEvent event : events) {
            apply(event);
        }
    }

    public String getAccountId() {
        return accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public boolean isCreated() {
        return created;
    }
}
