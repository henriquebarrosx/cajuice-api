package com.cajuice.app.controller.v1;

import java.time.LocalDate;
import java.util.List;

import com.cajuice.app.domain.dto.PartialUpdateTransactionRequestDTO;
import com.cajuice.app.domain.mapper.TransactionMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cajuice.app.domain.dto.CreateTransactionRequestDTO;
import com.cajuice.app.domain.dto.TransactionResponseDTO;
import com.cajuice.app.domain.entity.Transaction;
import com.cajuice.app.service.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
@Tag(name = "Transações", description = "Endpoints para gestão de transações")
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @Operation(summary = "Cria uma nova transação")
    @PostMapping()
    public ResponseEntity<TransactionResponseDTO> createTransaction(
            @Valid @RequestBody CreateTransactionRequestDTO request) {
        Transaction transaction = transactionService.create(request);
        TransactionResponseDTO response = transactionMapper.toResponseDTO(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Realiza o extrato entre duas datas")
    @GetMapping()
    public ResponseEntity<List<TransactionResponseDTO>> getTransactions(
            @RequestParam Long telegramId, @RequestParam LocalDate startAt, @RequestParam LocalDate endAt) {
        List<Transaction> transactions = transactionService.getBetweenDates(telegramId, startAt, endAt);

        List<TransactionResponseDTO> response = transactions.stream()
                .map(transactionMapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Realiza a exclusão de uma transação")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Realiza a atualização parcial de uma transação")
    @PatchMapping("{id}")
    public ResponseEntity<TransactionResponseDTO> updateTransaction(
            @PathVariable Long id,
            @Valid @RequestBody PartialUpdateTransactionRequestDTO request) {

        Transaction transaction = transactionService.update(id, request);
        TransactionResponseDTO response = transactionMapper.toResponseDTO(transaction);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
