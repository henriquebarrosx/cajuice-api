package com.cajuice.app.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cajuice.app.domain.dto.CreateTransactionRequestDTO;
import com.cajuice.app.domain.entity.Account;
import com.cajuice.app.domain.entity.Transaction;
import com.cajuice.app.exception.NotFoundException;
import com.cajuice.app.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountService accountService;
    private final TransactionRepository transactionRepository;

    public Transaction create(CreateTransactionRequestDTO request) {
        Account account = accountService.getByTelegramId(request.getTelegramId());

        Transaction transaction = Transaction.builder()
                .account(account)
                .period(request.getPeriod())
                .amount(request.getAmount())
                .description(request.getDescription())
                .isSettled(request.getIsSettled())
                .transactionType(request.getTransactionType())
                .build();

        return transactionRepository.save(transaction);
    }

    public Transaction getById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transaction not found"));
    }

    public List<Transaction> getBetweenDates(Long telegramId, LocalDate startAt, LocalDate endAt) {
        return transactionRepository.findByAccountTelegramIdAndPeriodBetween(telegramId, startAt, endAt);
    }

    public void deleteById(Long id) {
        Transaction transaction = getById(id);
        transactionRepository.delete(transaction);
    }

}
