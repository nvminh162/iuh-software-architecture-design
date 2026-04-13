package com.nvminh162.bank_account.command.model;

import java.math.BigDecimal;

public record CommandResult(String accountId, BigDecimal balance, String message) {
}
