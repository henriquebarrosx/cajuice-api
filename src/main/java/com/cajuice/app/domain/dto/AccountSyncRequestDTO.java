package com.cajuice.app.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados enviados pelo Bot para sincronização da conta do utilizador")
public class AccountSyncRequestDTO {

    @Schema(description = "ID único do usuário fornecido pelo Telegram", example = "123456789", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O telegramId não pode ser nulo")
    private Long telegramId;

    @Schema(description = "Primeiro nome do usuário", example = "John", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O primeiro nome é obrigatório")
    @Size(max = 128, message = "O primeiro nome não pode passar de 128 caracteres")
    private String firstName;

    @Schema(description = "Apelido/Sobrenome do usuário", example = "Doe")
    @Size(max = 128, message = "O sobrenome não pode passar de 128 caracteres")
    private String lastName;

    @Schema(description = "Username do Telegram (sem o @)", example = "johndoe")
    @Size(max = 64, message = "O username não pode passar de 64 caracteres")
    private String username;

}
