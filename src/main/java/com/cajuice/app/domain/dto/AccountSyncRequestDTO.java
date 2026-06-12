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

    @Schema(description = "Primeiro nome do usuário", example = "John", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O primeiro nome é obrigatório")
    @Size(max = 20, message = "O primeiro nome não pode passar de 20 caracteres")
    private String firstName;

    @Schema(description = "Apelido/Sobrenome do usuário", example = "Doe")
    @Size(max = 20, message = "O sobrenome não pode passar de 20 caracteres")
    private String lastName;

    @Schema(description = "Username do Telegram (sem o @)", example = "johndoe")
    @Size(max = 20, message = "O username não pode passar de 20 caracteres")
    private String username;

}
