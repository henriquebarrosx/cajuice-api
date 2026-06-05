package com.cajuice.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cajuice.app.domain.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	Optional<Account> findByTelegramId(Long telegramId);

}
