package com.cajuice.app.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Optional;

@Builder
@Schema(description = "Dados de requisição para atualização parcial de uma transação")
public record PartialUpdateTransactionRequestDTO(
        @Schema(description = "Novo valor da transação (opcional)", example = "42.50")
        Optional<@DecimalMin(value = "0.01", message = "O valor da transação deve ser maior que zero") BigDecimal> amount,

        @Schema(description = "Nova descrição da transação (opcional)", example = "Cartão de crédito")
        Optional<@Size(max = 256, message = "A descrição não pode passar de 256 caracteres") String> description,

        @Schema(description = "Novo status de efetivação da transação (opcional)", example = "true")
        Optional<Boolean> isSettled
) {
}
