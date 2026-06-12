package com.cajuice.app.service;

import org.springframework.stereotype.Service;

import com.cajuice.app.domain.dto.AccountSyncRequestDTO;
import com.cajuice.app.domain.entity.Account;
import com.cajuice.app.exception.NotFoundException;
import com.cajuice.app.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public Account syncAccount(Long telegramId, AccountSyncRequestDTO request) {
        Account account = accountRepository.findByTelegramId(telegramId)
                .orElseGet(() -> Account.builder().telegramId(telegramId).build());

        account.setFirstName(request.getFirstName());
        account.setLastName(request.getLastName());
        account.setUsername(request.getUsername());

        return accountRepository.save(account);
    }

    public Account getByTelegramId(Long telegramId) {
        return accountRepository.findByTelegramId(telegramId)
                .orElseThrow(() -> new NotFoundException("Account not found"));
    }

}
