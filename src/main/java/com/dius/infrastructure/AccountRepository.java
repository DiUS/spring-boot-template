package com.dius.infrastructure;

import com.dius.domain.Account;
import org.springframework.stereotype.Repository;

import javax.validation.ValidationException;
import javax.ws.rs.NotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Repository
public class AccountRepository {

    Map<Long, Account> accounts = new HashMap<>();

    public AccountRepository() {
        accounts.put(1L, new Account(1L, "Nicole", "99 Queen st"));
    }

    public Account create(Account account) {
        accounts.put(account.getId(), account);
        return account;
    }

    public Account update(Account account) {
        ofNullable(accounts.get(account.getId())).orElseThrow(() -> new NotFoundException("Account not found"));
        accounts.put(account.getId(), account);
        return account;
    }

    public Account remove(Long id) {
        final Account account = ofNullable(accounts.get(id)).orElseThrow(() -> new NotFoundException("Account not found"));
        accounts.remove(id);
        return account;
    }

    public Account get(Long id) {
        return ofNullable(accounts.get(id)).orElseThrow(() -> new NotFoundException("Account not found"));
    }

    public List<Account> all() {
        return accounts.entrySet()
                .stream().map(entry -> entry.getValue())
                .collect(toList());

    }
}
