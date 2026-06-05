package com.cajuice.app.domain.mapper;

import com.cajuice.app.domain.dto.AccountResponseDTO;
import com.cajuice.app.domain.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public AccountResponseDTO toResponseDTO(Account account) {
        return AccountResponseDTO.builder()
                .id(account.getId())
                .telegramId(account.getTelegramId())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .username(account.getUsername())
                .build();
    }

}
