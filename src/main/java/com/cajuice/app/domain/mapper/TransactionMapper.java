package com.cajuice.app.domain.mapper;

import com.cajuice.app.domain.dto.TransactionResponseDTO;
import com.cajuice.app.domain.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionResponseDTO toResponseDTO(Transaction transaction) {
        return TransactionResponseDTO.builder()
                .id(transaction.getId())
                .accountId(transaction.getAccount().getId())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .transactionType(transaction.getTransactionType())
                .period(transaction.getPeriod())
                .isSettled(transaction.getIsSettled())
                .build();
    }

}
