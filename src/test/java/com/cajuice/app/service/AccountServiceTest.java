package com.cajuice.app.service;

import com.cajuice.app.domain.dto.AccountSyncRequestDTO;
import com.cajuice.app.domain.entity.Account;
import com.cajuice.app.exception.NotFoundException;
import com.cajuice.app.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    AccountRepository repository;

    @InjectMocks
    AccountService accountService;

    @Captor
    private ArgumentCaptor<Account> accountCaptor;

    @Test
    void givenTelegramIdIsAlreadyRegistered_whenSyncingAccount_thenUpdateIt() {
        Long fakeTelegramId = 908070L;

        AccountSyncRequestDTO fakeRequest = AccountSyncRequestDTO.builder()
                .firstName("João")
                .lastName("Silva")
                .username("joaosilva")
                .build();

        Account fakeFoundAccount = Account.builder()
                .id(1L)
                .telegramId(fakeTelegramId)
                .firstName(fakeRequest.getFirstName())
                .lastName("Davino")
                .username("joaodavino")
                .build();

        when(repository.findByTelegramId(anyLong())).thenReturn(Optional.ofNullable(fakeFoundAccount));
        when(repository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Account account = accountService.syncAccount(fakeTelegramId, fakeRequest);

        verify(repository, times(1)).findByTelegramId(fakeTelegramId);
        verify(repository, times(1)).save(accountCaptor.capture());

        assertThat(account)
                .isNotNull()
                .isSameAs(accountCaptor.getValue());

        assertThat(accountCaptor.getValue())
                .returns(fakeTelegramId, Account::getTelegramId)
                .returns("João", Account::getFirstName)
                .returns("Silva", Account::getLastName)
                .returns("joaosilva", Account::getUsername);
    }

    @Test
    void givenTelegramIdDoesNotExist_whenSyncingAccount_thenCreateNewAccount() {
        Long fakeTelegramId = 908070L;

        AccountSyncRequestDTO fakeRequest = AccountSyncRequestDTO.builder()
                .firstName("João")
                .lastName("Silva")
                .username("joaosilva")
                .build();

        when(repository.findByTelegramId(anyLong())).thenReturn(Optional.empty());
        when(repository.save(any(Account.class))).thenAnswer(invocation -> {
            Account account = invocation.getArgument(0);
            account.setId(1L);
            return account;
        });

        Account account = accountService.syncAccount(fakeTelegramId, fakeRequest);

        verify(repository, times(1)).findByTelegramId(fakeTelegramId);
        verify(repository, times(1)).save(accountCaptor.capture());

        assertThat(account)
                .isNotNull()
                .returns(1L, Account::getId)
                .returns(fakeTelegramId, Account::getTelegramId)
                .returns("João", Account::getFirstName)
                .returns("Silva", Account::getLastName)
                .returns("joaosilva", Account::getUsername);
    }

    @Test
    void givenTelegramIdDoesNotExist_whenGettingAccountByTelegramId_thenThrowNotException() {
        Long fakeTelegramId = 908070L;

        when(repository.findByTelegramId(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> accountService.getByTelegramId(fakeTelegramId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Account not found");

        verify(repository, times(1)).findByTelegramId(fakeTelegramId);
    }

    @Test
    void givenTelegramIdExist_whenGettingAccountByTelegramId_thenReturnAccount() {
        Long fakeTelegramId = 908070L;

        Account fakeStoredAccount = Account.builder()
                .id(1L)
                .telegramId(fakeTelegramId)
                .firstName("João")
                .lastName("Silva")
                .username("joaosilva")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(repository.findByTelegramId(anyLong())).thenReturn(Optional.of(fakeStoredAccount));

        Account account = accountService.getByTelegramId(fakeTelegramId);

        verify(repository, times(1)).findByTelegramId(fakeTelegramId);

        assertThat(account).isNotNull().isSameAs(fakeStoredAccount);
    }

}
