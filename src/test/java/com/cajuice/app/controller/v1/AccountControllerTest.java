package com.cajuice.app.controller.v1;

import com.cajuice.app.domain.dto.AccountSyncRequestDTO;
import com.cajuice.app.domain.mapper.AccountMapper;
import com.cajuice.app.service.AccountService;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import tools.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    AccountMapper accountMapper;

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

}
