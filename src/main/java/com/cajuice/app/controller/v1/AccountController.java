package com.cajuice.app.controller.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cajuice.app.domain.dto.AccountSyncRequest;
import com.cajuice.app.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Tag(name = "Contas (V1)", description = "Endpoints para gestão de usuários vindos do Telegram")
public class AccountController {

	private final AccountService accountService;

	@Operation(summary = "Sincroniza conta do Telegram sem retorno de conteúdo", description = "Garante a existência da conta pelo telegram_id (Just-in-Time)")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Sincronização processada com sucesso (Sem conteúdo)"),
			@ApiResponse(responseCode = "400", description = "Dados da requisição inválidos")
	})
	@PostMapping("/sync")
	public ResponseEntity<Void> syncTelegramAccount(@Valid @RequestBody AccountSyncRequest request) {
		accountService.syncAccount(request);
		return ResponseEntity.noContent().build();
	}

}
