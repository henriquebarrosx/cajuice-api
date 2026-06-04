package com.cajuice.app.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.cajuice.app.domain.enums.TipoTransacao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Dados de retorno de uma transação financeira")
public record TransactionResponseDTO(
		@Schema(description = "ID do usuário do Telegram associado", example = "1310206331") Long telegramId,

		@Schema(description = "Valor da transação", example = "42.50") BigDecimal amount,

		@Schema(description = "Descrição do lançamento", example = "Almoço no restaurante") String description,

		@Schema(description = "Tipo da transação") TipoTransacao transactionType,

		@Schema(description = "Período correspondente (ano-mês-01)", example = "2026-06-01") LocalDate period,

		@Schema(description = "Indica se a transação já foi efetivada", example = "true") Boolean isSettled) {
}