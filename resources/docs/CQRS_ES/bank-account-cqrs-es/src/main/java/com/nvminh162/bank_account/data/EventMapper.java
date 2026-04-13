package com.nvminh162.bank_account.data;

import com.nvminh162.bank_account.command.event.AccountCreated;
import com.nvminh162.bank_account.command.event.AccountEvent;
import com.nvminh162.bank_account.command.event.MoneyDeposited;
import com.nvminh162.bank_account.command.event.MoneyWithdrawn;

import java.time.Instant;

public final class EventMapper {
    private EventMapper() {
    }

    public static EventEntity toEntity(AccountEvent event) {
        return new EventEntity(
                event.accountId(),
                event.getClass().getSimpleName(),
                event.amount(),
                Instant.now()
        );
    }

    public static AccountEvent toDomain(EventEntity entity) {
        return switch (entity.getEventType()) {
            case "AccountCreated" -> new AccountCreated(entity.getAccountId(), entity.getAmount());
            case "MoneyDeposited" -> new MoneyDeposited(entity.getAccountId(), entity.getAmount());
            case "MoneyWithdrawn" -> new MoneyWithdrawn(entity.getAccountId(), entity.getAmount());
            default -> throw new IllegalArgumentException("Unknown event type: " + entity.getEventType());
        };
    }
}
