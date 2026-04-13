package com.nvminh162.bank_account.query.projection;

import com.nvminh162.bank_account.command.aggregate.BankAccountAggregate;
import com.nvminh162.bank_account.command.event.AccountEvent;
import com.nvminh162.bank_account.data.EventMapper;
import com.nvminh162.bank_account.data.EventRepository;
import com.nvminh162.bank_account.query.model.AccountBalanceView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountProjectionService {
    private final EventRepository eventRepository;

    public AccountProjectionService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public AccountBalanceView getBalance(String accountId) {
        List<AccountEvent> history = eventRepository.findByAccountIdOrderByIdAsc(accountId)
                .stream()
                .map(EventMapper::toDomain)
                .toList();
        if (history.isEmpty()) {
            throw new IllegalArgumentException("Account not found: " + accountId);
        }

        BankAccountAggregate account = new BankAccountAggregate();
        account.replay(history);
        return new AccountBalanceView(accountId, account.getBalance(), history.size());
    }
}
