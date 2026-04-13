package com.nvminh162.bank_account.command.event;

import java.math.BigDecimal;

public record MoneyDeposited(String accountId, BigDecimal amount) implements AccountEvent {
}
