package com.cajuice.app.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.cajuice.app.domain.enums.TipoTransacao;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados enviados pelo Bot para criação de uma transação")
public class CreateTransactionRequestDTO {

	@Schema(description = "ID único do usuário fornecido pelo Telegram", example = "123456789", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotNull(message = "O telegramId não pode ser nulo")
	private Long telegramId;

	@NotNull(message = "O valor da transação é obrigatório")
	@DecimalMin(value = "0.01", message = "O valor da transação deve ser maior que zero")
	private BigDecimal amount;

	@NotBlank(message = "A descrição é obrigatória")
	@Size(max = 256, message = "A descrição não pode passar de 256 caracteres")
	private String description;

	@NotNull(message = "O tipo de transação (RECEITA/DESPESA) é obrigatório")
	private TipoTransacao transactionType;

	@NotNull(message = "O período/data da transação é obrigatório")
	private LocalDate period;

	@NotNull(message = "O status de efetivação deve ser informado")
	private Boolean isSettled;

}
