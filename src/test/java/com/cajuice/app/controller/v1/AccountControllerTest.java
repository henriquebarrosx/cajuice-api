package com.cajuice.app.controller.v1;

import com.cajuice.app.domain.dto.AccountSyncRequestDTO;
import com.cajuice.app.domain.entity.Account;
import com.cajuice.app.domain.mapper.AccountMapper;
import com.cajuice.app.service.AccountService;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import tools.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
@Import(AccountMapper.class)
public class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    AccountService accountService;

    @Test
    void givenEmptyFirstName_whenSyncAccount_thenReturn400() throws Exception {
        var fakeTelegramId = Long.valueOf(139028);

        var fakeRequest = AccountSyncRequestDTO.builder()
                .lastName("Silva")
                .username("joaosilva")
                .build();

        mockMvc
                .perform(
                        post("/api/v1/accounts/sync")
                                .header("X-Telegram-Id", fakeTelegramId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(fakeRequest))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.firstName").value("O primeiro nome é obrigatório"));

        verifyNoInteractions(accountService);
    }

    @Test
    void givenFirstNameHasSizeBiggerThan20_whenSyncAccount_thenReturn400() throws Exception {
        var fakeTelegramId = Long.valueOf(139028);

        var fakeRequest = AccountSyncRequestDTO.builder()
                .firstName("primeiroNomeAleatorioComMaisDe20Caracteres")
                .lastName("Silva")
                .username("joaosilva")
                .build();

        mockMvc
                .perform(
                        post("/api/v1/accounts/sync")
                                .header("X-Telegram-Id", fakeTelegramId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(fakeRequest))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.firstName").value("O primeiro nome não pode passar de 20 caracteres"));

        verifyNoInteractions(accountService);
    }

    @Test
    void givenEmptyLastName_whenSyncAccount_thenReturn200() throws Exception {
        var fakeTelegramId = Long.valueOf(139028);

        var fakeRequest = AccountSyncRequestDTO.builder()
                .firstName("João")
                .username("joaosilva")
                .build();

        var fakeAccount = Account.builder()
                .id(1L)
                .firstName(fakeRequest.getFirstName())
                .lastName(fakeRequest.getLastName())
                .username(fakeRequest.getUsername())
                .telegramId(fakeTelegramId)
                .build();

        when(accountService.syncAccount(anyLong(), any(AccountSyncRequestDTO.class)))
                .thenReturn(fakeAccount);

        mockMvc
                .perform(
                        post("/api/v1/accounts/sync")
                                .header("X-Telegram-Id", fakeTelegramId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(fakeRequest))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(fakeAccount.getId()))
                .andExpect(jsonPath("$.firstName").value(fakeAccount.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(fakeAccount.getLastName()))
                .andExpect(jsonPath("$.username").value(fakeAccount.getUsername()))
                .andExpect(jsonPath("$.telegramId").value(fakeAccount.getTelegramId()));

        verify(accountService).syncAccount(eq(fakeTelegramId), refEq(fakeRequest));
    }

    @Test
    void givenLastNameHasSizeBiggerThan20_whenSyncAccount_thenReturn400() throws Exception {
        var fakeTelegramId = Long.valueOf(139028);

        var fakeRequest = AccountSyncRequestDTO.builder()
                .firstName("João")
                .lastName("sobrenomeAleatorioComMaisDe20Caracteres")
                .username("joaosilva")
                .build();

        mockMvc
                .perform(
                        post("/api/v1/accounts/sync")
                                .header("X-Telegram-Id", fakeTelegramId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(fakeRequest))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.lastName").value("O sobrenome não pode passar de 20 caracteres"));

        verifyNoInteractions(accountService);
    }

    @Test
    void givenEmptyUsername_whenSyncAccount_thenReturn200() throws Exception {
        var fakeTelegramId = Long.valueOf(139028);

        var fakeRequest = AccountSyncRequestDTO.builder()
                .firstName("João")
                .lastName("Silva")
                .build();

        var fakeAccount = Account.builder()
                .id(1L)
                .firstName(fakeRequest.getFirstName())
                .lastName(fakeRequest.getLastName())
                .username(fakeRequest.getUsername())
                .telegramId(fakeTelegramId)
                .build();

        when(accountService.syncAccount(anyLong(), any(AccountSyncRequestDTO.class)))
                .thenReturn(fakeAccount);

        mockMvc
                .perform(
                        post("/api/v1/accounts/sync")
                                .header("X-Telegram-Id", fakeTelegramId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(fakeRequest))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(fakeAccount.getId()))
                .andExpect(jsonPath("$.firstName").value(fakeAccount.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(fakeAccount.getLastName()))
                .andExpect(jsonPath("$.username").value(fakeAccount.getUsername()))
                .andExpect(jsonPath("$.telegramId").value(fakeAccount.getTelegramId()));

        verify(accountService).syncAccount(eq(fakeTelegramId), refEq(fakeRequest));
    }

    @Test
    void givenUsernameHasSizeBiggerThan20_whenSyncAccount_thenReturn400() throws Exception {
        var fakeTelegramId = Long.valueOf(139028);

        var fakeRequest = AccountSyncRequestDTO.builder()
                .firstName("João")
                .lastName("Silva")
                .username("usernameAleatorioComMaisDe20Caracteres")
                .build();

        mockMvc
                .perform(
                        post("/api/v1/accounts/sync")
                                .header("X-Telegram-Id", fakeTelegramId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(fakeRequest))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.username").value("O username não pode passar de 20 caracteres"));

        verifyNoInteractions(accountService);
    }

}
