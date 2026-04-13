package com.nvminh162.bank_account.command.event;

import java.math.BigDecimal;

public interface AccountEvent {
    String accountId();

    BigDecimal amount();
}
