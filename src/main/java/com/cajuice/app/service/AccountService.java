package com.cajuice.app.service;

import org.springframework.stereotype.Service;

import com.cajuice.app.domain.dto.AccountSyncRequest;
import com.cajuice.app.domain.entity.Account;
import com.cajuice.app.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AccountService {

	private final AccountRepository accountRepository;

	public Account syncAccount(AccountSyncRequest request) {
		Account account = accountRepository.findByTelegramId(request.getTelegramId())
				.orElseGet(() -> Account.builder()
						.telegramId(request.getTelegramId())
						.firstName(request.getFirstName())
						.lastName(request.getLastName())
						.username(request.getUsername())
						.build());

		return accountRepository.save(account);
	}

}
