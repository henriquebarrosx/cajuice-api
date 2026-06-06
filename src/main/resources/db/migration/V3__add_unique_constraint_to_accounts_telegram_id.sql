ALTER TABLE accounts
    ADD CONSTRAINT uq_accounts_telegram_id UNIQUE (telegram_id);