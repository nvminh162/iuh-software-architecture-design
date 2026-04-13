package com.nvminh162.bank_account.command.model;

import java.math.BigDecimal;

public record CreateAccountRequest(String accountId, BigDecimal initialBalance) {
}
