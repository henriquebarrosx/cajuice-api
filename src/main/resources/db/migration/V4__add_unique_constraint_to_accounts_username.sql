ALTER TABLE accounts
    ADD CONSTRAINT uq_accounts_username UNIQUE (username);