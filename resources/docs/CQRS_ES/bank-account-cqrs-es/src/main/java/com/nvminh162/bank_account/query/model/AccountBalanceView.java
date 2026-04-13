package com.nvminh162.bank_account.query.model;

import java.math.BigDecimal;

public record AccountBalanceView(String accountId, BigDecimal balance, int eventCount) {
}
