package com.cajuice.app.controller.v1;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

	@Operation(summary = "Cria uma nova transação")
	@PostMapping()
	public ResponseEntity<TransactionResponseDTO> createTransaction(
			@Valid @RequestBody CreateTransactionRequestDTO request) {
		Transaction transaction = transactionService.create(request);

		TransactionResponseDTO response = TransactionResponseDTO.builder()
				.telegramId(request.getTelegramId())
				.amount(transaction.getAmount())
				.description(transaction.getDescription())
				.transactionType(transaction.getTransactionType())
				.period(transaction.getPeriod())
				.isSettled(transaction.getIsSettled())
				.build();

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@Operation(summary = "Realiza o extrato entre duas datas")
	@GetMapping()
	public ResponseEntity<List<TransactionResponseDTO>> getTransactions(
			@RequestParam Long telegramId, @RequestParam LocalDate startAt, @RequestParam LocalDate endAt) {
		List<Transaction> transactions = transactionService.getBetweenDates(telegramId, startAt, endAt);

		List<TransactionResponseDTO> response = transactions.stream()
				.map(transaction -> TransactionResponseDTO.builder()
						.telegramId(telegramId)
						.amount(transaction.getAmount())
						.description(transaction.getDescription())
						.transactionType(transaction.getTransactionType())
						.period(transaction.getPeriod())
						.isSettled(transaction.getIsSettled())
						.build())
				.toList();

		return ResponseEntity.ok().body(response);
	}

	@Operation(summary = "Realiza a exclusão de uma transação")
	@DeleteMapping("{id}")
	public ResponseEntity<List<TransactionResponseDTO>> deleteTransaction(@PathVariable("id") Long id) {
		transactionService.deleteById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
