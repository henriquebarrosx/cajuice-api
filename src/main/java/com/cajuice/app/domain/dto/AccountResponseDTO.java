package com.cajuice.app.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Dados de retorno da conta vinculado ao Telegram ID")
public record AccountResponseDTO(
        @Schema(description = "ID do usuário")
        Long id,

        @Schema(description = "ID do usuário associado ao Telegram", example = "1310206331")
        Long telegramId,

        @Schema(description = "Primeiro nome", example = "John")
        String firstName,

        @Schema(description = "Último nome", example = "Doe")
        String lastName,

        @Schema(description = "Nome de usuário", example = "johndoe")
        String username
) {
}
